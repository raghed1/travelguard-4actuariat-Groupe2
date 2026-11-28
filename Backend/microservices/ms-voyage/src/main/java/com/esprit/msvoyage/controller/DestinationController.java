package com.esprit.msvoyage.controller;

import com.esprit.msvoyage.dto.DestinationRequest;
import com.esprit.msvoyage.entity.Destination;
import com.esprit.msvoyage.service.DestinationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/destinations")
@Tag(name = "Destinations", description = "Gestion des destinations de voyage")
@RequiredArgsConstructor
public class DestinationController {

    private final DestinationService service;
    private final Environment environment;

    @Operation(summary = "Lister toutes les destinations")
    @GetMapping
    public List<Destination> getAll() {
        System.out.println("Requête reçue sur le port " + environment.getProperty("local.server.port"));
        return service.findAll();
    }

    @Operation(summary = "Récupérer une destination par ID")
    @ApiResponse(responseCode = "200", description = "Destination trouvée")
    @ApiResponse(responseCode = "404", description = "Destination introuvable")
    @GetMapping("/{id}")
    public Destination getById(
            @Parameter(description = "ID de la destination", example = "1") @PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(summary = "Créer une nouvelle destination")
    @ApiResponse(responseCode = "201", description = "Destination créée avec succès")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Destination create(@Valid @RequestBody DestinationRequest request) {
        return service.create(request);
    }

    @Operation(summary = "Mettre à jour une destination")
    @ApiResponse(responseCode = "200", description = "Destination mise à jour")
    @PutMapping("/{id}")
    public Destination update(
            @Parameter(description = "ID de la destination", example = "1") @PathVariable Long id,
            @Valid @RequestBody DestinationRequest request) {
        return service.update(id, request);
    }

    @Operation(summary = "Supprimer une destination")
    @ApiResponse(responseCode = "204", description = "Destination supprimée")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @Parameter(description = "ID de la destination", example = "1") @PathVariable Long id) {
        service.delete(id);
    }
}