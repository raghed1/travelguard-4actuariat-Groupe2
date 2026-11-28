package com.travelguard.reclamations.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/** Trace d'un changement de statut ou d'une action sur une reclamation. */
@Document(collection = "claim_histories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClaimHistory {

    @Id
    private Long id;

    @Indexed
    private Long claimId;

    /** null a la creation. */
    private ClaimStatus previousStatus;
    private ClaimStatus newStatus;
    private String comment;
    private LocalDateTime changeDate;
}
