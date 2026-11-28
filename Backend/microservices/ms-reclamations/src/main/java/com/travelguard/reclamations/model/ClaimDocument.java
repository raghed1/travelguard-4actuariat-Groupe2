package com.travelguard.reclamations.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/** Justificatif : le fichier est dans un stockage externe, on ne garde que son URL. */
@Document(collection = "claim_documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClaimDocument {

    @Id
    private Long id;

    @Indexed
    private Long claimId;

    private String documentName;
    private DocumentType documentType;
    private String documentUrl;
    private LocalDateTime uploadDate;
}
