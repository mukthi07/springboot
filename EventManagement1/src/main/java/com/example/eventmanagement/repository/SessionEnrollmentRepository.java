package com.example.eventmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eventmanagement.entity.SessionEnrollment;

public interface SessionEnrollmentRepository
        extends JpaRepository<SessionEnrollment, Long> {

    boolean existsBySessionSessionIdAndAttendeeAttendeeId(
            Long sessionId, Long attendeeId);

}