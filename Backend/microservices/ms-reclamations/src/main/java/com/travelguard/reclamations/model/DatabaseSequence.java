package com.travelguard.reclamations.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/** Compteur auto-incremente : MongoDB ne genere pas d'ID Long (1, 2, 3...) tout seul. */
@Document(collection = "database_sequences")
@Getter
@Setter
@NoArgsConstructor
public class DatabaseSequence {

    @Id
    private String id;
    private long seq;
}
