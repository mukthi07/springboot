package com.example.eventmanagement.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.eventmanagement.dto.*;
import com.example.eventmanagement.entity.Attendee;
import com.example.eventmanagement.exception.DuplicateResourceException;
import com.example.eventmanagement.exception.ResourceNotFoundException;
import com.example.eventmanagement.repository.AttendeeRepository;

@Service
public class AttendeeService {

    private final AttendeeRepository repository;

    public AttendeeService(AttendeeRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public AttendeeResponseDTO createAttendee(
            AttendeeRequestDTO dto) {

        if (repository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException(
                    "Email already registered");
        }

        Attendee attendee = new Attendee();

        attendee.setAttendeeName(dto.getAttendeeName());
        attendee.setEmail(dto.getEmail());
        attendee.setPhone(dto.getPhone());

        Attendee saved = repository.save(attendee);

        return toDTO(saved);
    }

    public AttendeeResponseDTO getAttendee(Long id) {

        Attendee attendee = repository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Attendee not found"));

        return toDTO(attendee);
    }

    private AttendeeResponseDTO toDTO(Attendee attendee) {

        AttendeeResponseDTO dto = new AttendeeResponseDTO();

        dto.setAttendeeId(attendee.getAttendeeId());
        dto.setAttendeeName(attendee.getAttendeeName());
        dto.setEmail(attendee.getEmail());
        dto.setPhone(attendee.getPhone());

        return dto;
    }
}