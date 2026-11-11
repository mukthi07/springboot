package com.example.eventmanagement.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.example.eventmanagement.entity.Venue;
import com.example.eventmanagement.repository.VenueRepository;

@RestController
@RequestMapping("/api/venues")
public class VenueController {

    private final VenueRepository repository;

    public VenueController(VenueRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<Venue> addVenue(
            @RequestBody Venue venue) {

        Venue saved = repository.save(venue);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }

    @GetMapping
    public ResponseEntity<?> getAllVenues() {

        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venue> getVenue(
            @PathVariable Long id) {

        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}