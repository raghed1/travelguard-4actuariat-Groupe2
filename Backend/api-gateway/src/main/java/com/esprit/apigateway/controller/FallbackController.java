package com.esprit.apigateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
@RestController
public class FallbackController {

    @RequestMapping("/fallback/voyage")
    public ResponseEntity<Map<String, Object>> voyageFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Map.of(
                "message", "Service Voyage temporairement indisponible",
                "degraded", true));
    }

    @RequestMapping("/fallback/reclamation")
    public ResponseEntity<Map<String, Object>> reclamationFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Map.of(
                "message", "Service Reclamation temporairement indisponible",
                "degraded", true));
    }

}