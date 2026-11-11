package com.example.eventmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eventmanagement.entity.Attendee;

public interface AttendeeRepository
        extends JpaRepository<Attendee, Long> {

    Optional<Attendee> findByEmail(String email);

    boolean existsByEmail(String email);
}