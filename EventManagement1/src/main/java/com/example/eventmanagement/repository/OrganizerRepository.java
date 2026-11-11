package com.example.eventmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eventmanagement.entity.Organizer;

public interface OrganizerRepository
        extends JpaRepository<Organizer, Long> {

    Optional<Organizer> findByEmail(String email);

    boolean existsByEmail(String email);
}