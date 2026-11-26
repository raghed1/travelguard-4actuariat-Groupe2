package com.esprit.msvoyage.controller;

import com.esprit.msvoyage.entity.Destination;
import com.esprit.msvoyage.service.DestinationService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/destinations")
@RequiredArgsConstructor
public class DestinationController {
    private final DestinationService service;
    private final Environment environment;

    @GetMapping
    public List<Destination> getAll() {
        System.out.println("Requête reçue sur le port " + environment.getProperty("local.server.port"));
        return service.findAll();
    }
}