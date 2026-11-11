package com.example.eventmanagement.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.example.eventmanagement.entity.Organizer;
import com.example.eventmanagement.service.OrganizerService;

@RestController
@RequestMapping("/api/organizers")
public class OrganizerController {

    private final OrganizerService service;

    public OrganizerController(OrganizerService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody Organizer organizer) {

        service.registerOrganizer(organizer);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Organizer registered successfully");
    }
}