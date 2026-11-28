package com.travelguard.reclamations.repository;

import com.travelguard.reclamations.model.ClaimDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ClaimDocumentRepository extends MongoRepository<ClaimDocument, Long> {

    List<ClaimDocument> findByClaimIdOrderByUploadDateAsc(Long claimId);

    void deleteByClaimId(Long claimId);
}
