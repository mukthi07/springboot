package com.example.eventmanagement.controller;

import jakarta.validation.Valid;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.example.eventmanagement.dto.*;
import com.example.eventmanagement.service.AttendeeService;

@RestController
@RequestMapping("/api/attendees")
public class AttendeeController {

    private final AttendeeService service;

    public AttendeeController(AttendeeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AttendeeResponseDTO> createAttendee(
            @Valid @RequestBody AttendeeRequestDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.createAttendee(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttendeeResponseDTO> getAttendee(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.getAttendee(id));
    }
}