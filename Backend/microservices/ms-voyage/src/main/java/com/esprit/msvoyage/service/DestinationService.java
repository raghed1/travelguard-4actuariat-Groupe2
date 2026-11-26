package com.esprit.msvoyage.service;

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
}