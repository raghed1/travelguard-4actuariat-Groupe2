package com.travelguard.reclamations.controller;

import com.travelguard.reclamations.model.Claim;
import com.travelguard.reclamations.model.ClaimDocument;
import com.travelguard.reclamations.model.ClaimHistory;
import com.travelguard.reclamations.model.ClaimStatus;
import com.travelguard.reclamations.service.ClaimService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/claims")
public class ClaimController {

    private final ClaimService claimService;

    @Value("${server.port}")
    private String serverPort;

    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    /** Test rapide Eureka / Gateway / Load Balancing : ne touche pas MongoDB. */
    @GetMapping("/ping")
    public Map<String, Object> ping() {
        System.out.println("Requete recue par l'instance reclamation : " + serverPort);
        return Map.of("service", "ms-reclamations", "status", "UP", "port", serverPort);
    }

    // ---------- Claims ----------

    @PostMapping
    public ResponseEntity<Claim> create(@RequestBody Claim claim) {
        Claim created = claimService.create(claim);
        return ResponseEntity.created(URI.create("/api/claims/" + created.getId())).body(created);
    }

    @GetMapping
    public List<Claim> getAll(@RequestParam(required = false) ClaimStatus status) {
        return claimService.getAll(status);
    }

    @GetMapping("/{id}")
    public Claim getById(@PathVariable Long id) {
        return claimService.getById(id);
    }

    @GetMapping("/client/{clientId}")
    public List<Claim> getByClient(@PathVariable Long clientId) {
        return claimService.getByClient(clientId);
    }

    @PutMapping("/{id}")
    public Claim update(@PathVariable Long id, @RequestBody Claim claim) {
        return claimService.update(id, claim);
    }

    /** Corps attendu : { "status": "APPROVED", "comment": "..." } */
    @PutMapping("/{id}/status")
    public Claim changeStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return claimService.changeStatus(id, body.get("status"), body.get("comment"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        claimService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ---------- Documents ----------

    @PostMapping("/{id}/documents")
    public ResponseEntity<ClaimDocument> addDocument(@PathVariable Long id, @RequestBody ClaimDocument document) {
        return ResponseEntity.status(201).body(claimService.addDocument(id, document));
    }

    @GetMapping("/{id}/documents")
    public List<ClaimDocument> getDocuments(@PathVariable Long id) {
        return claimService.getDocuments(id);
    }

    // ---------- History ----------

    @GetMapping("/{id}/history")
    public List<ClaimHistory> getHistory(@PathVariable Long id) {
        return claimService.getHistory(id);
    }
}
