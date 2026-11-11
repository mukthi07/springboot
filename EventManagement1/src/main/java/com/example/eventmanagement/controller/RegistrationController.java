package com.example.eventmanagement.controller;

import jakarta.validation.Valid;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.example.eventmanagement.dto.RegistrationRequestDTO;
import com.example.eventmanagement.entity.Registration;
import com.example.eventmanagement.entity.SessionEnrollment;
import com.example.eventmanagement.service.RegistrationService;

@RestController
@RequestMapping("/api/registrations")
public class RegistrationController {

    private final RegistrationService service;

    public RegistrationController(RegistrationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Registration> registerAttendee(
            @Valid @RequestBody RegistrationRequestDTO dto) {

        Registration registration =
                service.registerAttendee(
                        dto.getAttendeeId(),
                        dto.getEventId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(registration);
    }

    @PostMapping("/enroll")
    public ResponseEntity<SessionEnrollment> enrollSession(
            @RequestParam Long attendeeId,
            @RequestParam Long sessionId) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.enrollSession(
                        attendeeId, sessionId));
    }
}