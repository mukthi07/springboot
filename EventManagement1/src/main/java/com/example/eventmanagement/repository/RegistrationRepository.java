package com.example.eventmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eventmanagement.entity.Registration;

public interface RegistrationRepository
        extends JpaRepository<Registration, Long> {

    boolean existsByAttendeeAttendeeIdAndEventEventId(
            Long attendeeId, Long eventId);

    List<Registration> findByEventEventId(Long eventId);

    List<Registration> findByAttendeeAttendeeId(
            Long attendeeId);
}