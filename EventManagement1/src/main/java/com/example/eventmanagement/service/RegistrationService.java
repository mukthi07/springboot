package com.example.eventmanagement.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.eventmanagement.entity.*;
import com.example.eventmanagement.exception.*;
import com.example.eventmanagement.repository.*;

@Service
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final AttendeeRepository attendeeRepository;
    private final EventRepository eventRepository;
    private final SessionRepository sessionRepository;
    private final SessionEnrollmentRepository enrollmentRepository;

    public RegistrationService(
            RegistrationRepository registrationRepository,
            AttendeeRepository attendeeRepository,
            EventRepository eventRepository,
            SessionRepository sessionRepository,
            SessionEnrollmentRepository enrollmentRepository) {

        this.registrationRepository = registrationRepository;
        this.attendeeRepository = attendeeRepository;
        this.eventRepository = eventRepository;
        this.sessionRepository = sessionRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    // REGISTER ATTENDEE FOR EVENT
    @Transactional
    public Registration registerAttendee(
            Long attendeeId, Long eventId) {

        Attendee attendee = attendeeRepository
                .findById(attendeeId)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Attendee not found"));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Event not found"));

        if (registrationRepository
                .existsByAttendeeAttendeeIdAndEventEventId(
                        attendeeId, eventId)) {

            throw new DuplicateResourceException(
                    "Attendee already registered for this event");
        }

        Registration registration = new Registration();

        registration.setAttendee(attendee);
        registration.setEvent(event);
        registration.setRegistrationDate(LocalDateTime.now());
        registration.setStatus("CONFIRMED");

        return registrationRepository.save(registration);
    }

    // ENROLL ATTENDEE IN SESSION
    @Transactional
    public SessionEnrollment enrollSession(
            Long attendeeId, Long sessionId) {

        Attendee attendee = attendeeRepository
                .findById(attendeeId)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Attendee not found"));

        Session session = sessionRepository
                .findByIdForUpdate(sessionId);

        if (session == null) {
            throw new ResourceNotFoundException(
                    "Session not found");
        }

        Long eventId = session.getEvent().getEventId();

        if (!registrationRepository
                .existsByAttendeeAttendeeIdAndEventEventId(
                        attendeeId, eventId)) {

            throw new IllegalArgumentException(
                    "Register for the event before enrolling in its session");
        }

        if (enrollmentRepository
                .existsBySessionSessionIdAndAttendeeAttendeeId(
                        sessionId, attendeeId)) {

            throw new DuplicateResourceException(
                    "Already enrolled in this session");
        }

        if (session.getEnrolledCount() >= session.getCapacity()) {

            throw new CapacityExceededException(
                    "Session capacity is full");
        }

        SessionEnrollment enrollment =
                new SessionEnrollment();

        enrollment.setAttendee(attendee);
        enrollment.setSession(session);
        enrollment.setEnrollmentDate(LocalDateTime.now());

        session.setEnrolledCount(
                session.getEnrolledCount() + 1);

        sessionRepository.save(session);

        return enrollmentRepository.save(enrollment);
    }
}