package com.travelguard.reclamations.repository;

import com.travelguard.reclamations.model.Claim;
import com.travelguard.reclamations.model.ClaimStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ClaimRepository extends MongoRepository<Claim, Long> {

    List<Claim> findByClientId(Long clientId);

    List<Claim> findByStatus(ClaimStatus status);
}
