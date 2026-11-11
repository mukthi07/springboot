package com.example.eventmanagement.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.example.eventmanagement.dto.*;
import com.example.eventmanagement.service.SessionService;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    private final SessionService service;

    public SessionController(SessionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<SessionResponseDTO> createSession(
            @Valid @RequestBody SessionRequestDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.createSession(dto));
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<SessionResponseDTO>>
            getSessions(@PathVariable Long eventId) {

        return ResponseEntity.ok(
                service.getSessionsByEvent(eventId));
    }
}