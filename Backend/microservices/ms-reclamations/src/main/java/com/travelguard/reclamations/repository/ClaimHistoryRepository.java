package com.travelguard.reclamations.repository;

import com.travelguard.reclamations.model.ClaimHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ClaimHistoryRepository extends MongoRepository<ClaimHistory, Long> {

    List<ClaimHistory> findByClaimIdOrderByChangeDateAsc(Long claimId);

    void deleteByClaimId(Long claimId);
}
