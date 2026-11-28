package com.esprit.msvoyage.service;

import com.esprit.msvoyage.dto.DestinationRequest;
import com.esprit.msvoyage.entity.Destination;
import com.esprit.msvoyage.repository.DestinationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DestinationService {

    private final DestinationRepository repository;

    public List<Destination> findAll() {
        return repository.findAll();
    }

    public Destination findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Destination introuvable avec l'id : " + id));
    }

    public Destination create(DestinationRequest request) {
        Destination destination = new Destination();
        destination.setCountry(request.country());
        destination.setCity(request.city());
        destination.setDescription(request.description());
        return repository.save(destination);
    }

    public Destination update(Long id, DestinationRequest request) {
        Destination destination = findById(id);
        destination.setCountry(request.country());
        destination.setCity(request.city());
        destination.setDescription(request.description());
        return repository.save(destination);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Destination introuvable avec l'id : " + id);
        }
        repository.deleteById(id);
    }
}