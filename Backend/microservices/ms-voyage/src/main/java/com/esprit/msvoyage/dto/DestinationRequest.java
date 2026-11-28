package com.esprit.msvoyage.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Requête pour créer ou modifier une destination")
public record DestinationRequest(
        @Schema(description = "Pays de la destination", example = "France")
        @NotBlank(message = "Le pays est obligatoire")
        String country,

        @Schema(description = "Ville de la destination", example = "Paris")
        @NotBlank(message = "La ville est obligatoire")
        String city,

        @Schema(description = "Description de la destination", example = "Voyage romantique")
        String description
) {}