package com.travelguard.reclamations.service;

import com.travelguard.reclamations.model.Claim;
import com.travelguard.reclamations.model.ClaimDocument;
import com.travelguard.reclamations.model.ClaimHistory;
import com.travelguard.reclamations.model.ClaimStatus;
import com.travelguard.reclamations.repository.ClaimDocumentRepository;
import com.travelguard.reclamations.repository.ClaimHistoryRepository;
import com.travelguard.reclamations.repository.ClaimRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Logique metier de MS3 : reclamations, justificatifs et historique.
 * Les erreurs sont levees avec ResponseStatusException (404, 400, 409).
 */
@Service
public class ClaimService {

    private static final String CLAIM_SEQ = "claims_sequence";
    private static final String DOCUMENT_SEQ = "claim_documents_sequence";
    private static final String HISTORY_SEQ = "claim_histories_sequence";

    private final ClaimRepository claimRepository;
    private final ClaimDocumentRepository documentRepository;
    private final ClaimHistoryRepository historyRepository;
    private final SequenceGeneratorService sequences;

    public ClaimService(ClaimRepository claimRepository,
                        ClaimDocumentRepository documentRepository,
                        ClaimHistoryRepository historyRepository,
                        SequenceGeneratorService sequences) {
        this.claimRepository = claimRepository;
        this.documentRepository = documentRepository;
        this.historyRepository = historyRepository;
        this.sequences = sequences;
    }

    // ================= CLAIMS =================

    public Claim create(Claim claim) {
        if (claim.getClientId() == null || claim.getContractId() == null || claim.getTravelId() == null
                || claim.getClaimType() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "clientId, contractId, travelId et claimType sont obligatoires");
        }
        if (claim.getRequestedAmount() == null || claim.getRequestedAmount() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "requestedAmount doit etre superieur a 0");
        }

        // Ces champs ne sont jamais pris depuis le client
        LocalDateTime now = LocalDateTime.now();
        claim.setId(sequences.nextId(CLAIM_SEQ));
        claim.setStatus(ClaimStatus.SUBMITTED);
        claim.setRiskScore(null);
        claim.setRiskLevel(null);
        claim.setCreatedAt(now);
        claim.setUpdatedAt(now);

        // TODO (phase 4) : verifier le contrat (MS1) et le voyage (MS2) via OpenFeign,
        //                  puis publier l'evenement ClaimCreated sur RabbitMQ.
        Claim saved = claimRepository.save(claim);
        addHistory(saved.getId(), null, ClaimStatus.SUBMITTED, "Reclamation declaree");
        return saved;
    }

    public List<Claim> getAll(ClaimStatus status) {
        return status == null ? claimRepository.findAll() : claimRepository.findByStatus(status);
    }

    public Claim getById(Long id) {
        return claimRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Reclamation introuvable avec l'id " + id));
    }

    public List<Claim> getByClient(Long clientId) {
        return claimRepository.findByClientId(clientId);
    }

    public Claim update(Long id, Claim data) {
        Claim claim = getById(id);
        if (claim.getStatus() != ClaimStatus.SUBMITTED) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Modification possible uniquement au statut SUBMITTED (actuel : " + claim.getStatus() + ")");
        }
        if (data.getRequestedAmount() != null && data.getRequestedAmount() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "requestedAmount doit etre superieur a 0");
        }

        // Seuls ces champs sont modifiables (clientId, contractId, travelId, status : non)
        if (data.getClaimType() != null) claim.setClaimType(data.getClaimType());
        if (data.getDescription() != null) claim.setDescription(data.getDescription());
        if (data.getIncidentDate() != null) claim.setIncidentDate(data.getIncidentDate());
        if (data.getRequestedAmount() != null) claim.setRequestedAmount(data.getRequestedAmount());
        claim.setUpdatedAt(LocalDateTime.now());

        Claim saved = claimRepository.save(claim);
        addHistory(id, saved.getStatus(), saved.getStatus(), "Reclamation modifiee");
        return saved;
    }

    public Claim changeStatus(Long id, String newStatusValue, String comment) {
        Claim claim = getById(id);

        ClaimStatus target;
        try {
            target = ClaimStatus.valueOf(String.valueOf(newStatusValue).toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "status invalide. Valeurs possibles : SUBMITTED, IN_REVIEW, APPROVED, REJECTED");
        }

        ClaimStatus current = claim.getStatus();
        if (!current.canTransitionTo(target)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Changement de statut interdit : " + current + " -> " + target);
        }
        if (target == ClaimStatus.REJECTED && (comment == null || comment.isBlank())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Un commentaire est obligatoire pour refuser une reclamation");
        }

        claim.setStatus(target);
        claim.setUpdatedAt(LocalDateTime.now());
        Claim saved = claimRepository.save(claim);
        addHistory(id, current, target, comment);

        // TODO (phase 4) : si target == APPROVED -> publier ClaimApproved sur RabbitMQ (vers MS4).
        return saved;
    }

    public void delete(Long id) {
        Claim claim = getById(id);
        if (claim.getStatus() != ClaimStatus.SUBMITTED) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Suppression possible uniquement au statut SUBMITTED (actuel : " + claim.getStatus() + ")");
        }
        // Pas de cascade en MongoDB : on supprime les dependances a la main
        documentRepository.deleteByClaimId(id);
        historyRepository.deleteByClaimId(id);
        claimRepository.deleteById(id);
    }

    // ================= DOCUMENTS =================

    public ClaimDocument addDocument(Long claimId, ClaimDocument document) {
        Claim claim = getById(claimId);
        if (claim.getStatus().isFinal()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Impossible d'ajouter un justificatif : la reclamation est " + claim.getStatus());
        }
        if (document.getDocumentName() == null || document.getDocumentName().isBlank()
                || document.getDocumentType() == null
                || document.getDocumentUrl() == null || document.getDocumentUrl().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "documentName, documentType et documentUrl sont obligatoires");
        }

        document.setId(sequences.nextId(DOCUMENT_SEQ));
        document.setClaimId(claimId);
        document.setUploadDate(LocalDateTime.now());
        ClaimDocument saved = documentRepository.save(document);

        addHistory(claimId, claim.getStatus(), claim.getStatus(),
                "Justificatif ajoute : " + saved.getDocumentName());
        return saved;
    }

    public List<ClaimDocument> getDocuments(Long claimId) {
        getById(claimId); // 404 si la reclamation n'existe pas
        return documentRepository.findByClaimIdOrderByUploadDateAsc(claimId);
    }

    // ================= HISTORY =================

    public List<ClaimHistory> getHistory(Long claimId) {
        getById(claimId); // 404 si la reclamation n'existe pas
        return historyRepository.findByClaimIdOrderByChangeDateAsc(claimId);
    }

    private void addHistory(Long claimId, ClaimStatus previous, ClaimStatus next, String comment) {
        ClaimHistory entry = new ClaimHistory(sequences.nextId(HISTORY_SEQ), claimId,
                previous, next, comment, LocalDateTime.now());
        historyRepository.save(entry);
    }
}
