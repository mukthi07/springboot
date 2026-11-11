package com.example.eventmanagement.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.eventmanagement.dto.SessionRequestDTO;
import com.example.eventmanagement.dto.SessionResponseDTO;
import com.example.eventmanagement.entity.Event;
import com.example.eventmanagement.entity.Session;
import com.example.eventmanagement.exception.ResourceNotFoundException;
import com.example.eventmanagement.repository.EventRepository;
import com.example.eventmanagement.repository.SessionRepository;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final EventRepository eventRepository;

    public SessionService(
            SessionRepository sessionRepository,
            EventRepository eventRepository) {

        this.sessionRepository = sessionRepository;
        this.eventRepository = eventRepository;
    }

    @Transactional
    public SessionResponseDTO createSession(
            SessionRequestDTO dto) {

        if (!dto.getEndTime().isAfter(dto.getStartTime())) {
            throw new IllegalArgumentException(
                    "End time must be after start time");
        }

        Event event = eventRepository.findById(dto.getEventId())
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Event not found"));

        Session session = new Session();

        session.setSessionName(dto.getSessionName());
        session.setSpeaker(dto.getSpeaker());
        session.setStartTime(dto.getStartTime());
        session.setEndTime(dto.getEndTime());
        session.setCapacity(dto.getCapacity());
        session.setEnrolledCount(0);
        session.setEvent(event);

        return toDTO(sessionRepository.save(session));
    }

    public List<SessionResponseDTO> getSessionsByEvent(
            Long eventId) {

        return sessionRepository.findByEventEventId(eventId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    private SessionResponseDTO toDTO(Session session) {

        SessionResponseDTO dto = new SessionResponseDTO();

        dto.setSessionId(session.getSessionId());
        dto.setSessionName(session.getSessionName());
        dto.setSpeaker(session.getSpeaker());
        dto.setStartTime(session.getStartTime());
        dto.setEndTime(session.getEndTime());
        dto.setCapacity(session.getCapacity());
        dto.setEnrolledCount(session.getEnrolledCount());

        return dto;
    }
}