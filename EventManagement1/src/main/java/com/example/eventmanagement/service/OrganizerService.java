package com.example.eventmanagement.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.eventmanagement.entity.Organizer;
import com.example.eventmanagement.exception.DuplicateResourceException;
import com.example.eventmanagement.repository.OrganizerRepository;

@Service
public class OrganizerService {

    private final OrganizerRepository repository;
    private final PasswordEncoder encoder;

    public OrganizerService(
            OrganizerRepository repository,
            PasswordEncoder encoder) {

        this.repository = repository;
        this.encoder = encoder;
    }

    @Transactional
    public Organizer registerOrganizer(Organizer organizer) {

        if (repository.existsByEmail(organizer.getEmail())) {
            throw new DuplicateResourceException(
                    "Organizer email already exists");
        }

        organizer.setPassword(
                encoder.encode(organizer.getPassword()));

        organizer.setRole("ORGANIZER");

        return repository.save(organizer);
    }
}