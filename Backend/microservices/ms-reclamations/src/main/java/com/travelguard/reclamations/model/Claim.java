package com.travelguard.reclamations.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

/** Sinistre declare par un voyageur. clientId / contractId / travelId viennent de MS1 et MS2. */
@Document(collection = "claims")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Claim {

    @Id
    private Long id;

    @Indexed
    private Long clientId;
    private Long contractId;
    private Long travelId;

    private ClaimType claimType;
    private String description;
    private LocalDate incidentDate;
    private Double requestedAmount;

    @Indexed
    private ClaimStatus status;

    /** Renseignes plus tard par MS5 (TravelGuard AI). */
    private Double riskScore;
    private String riskLevel;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
