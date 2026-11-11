package com.example.eventmanagement.mapper;

import org.springframework.stereotype.Component;

import com.example.eventmanagement.dto.EventRequestDTO;
import com.example.eventmanagement.dto.EventResponseDTO;
import com.example.eventmanagement.entity.Event;

@Component
public class EventMapper {

    public Event toEntity(EventRequestDTO dto) {

        Event event = new Event();

        event.setEventName(dto.getEventName());
        event.setDescription(dto.getDescription());
        event.setEventDate(dto.getEventDate());

        return event;
    }

    public EventResponseDTO toResponseDTO(Event event) {

        EventResponseDTO dto = new EventResponseDTO();

        dto.setEventId(event.getEventId());
        dto.setEventName(event.getEventName());
        dto.setDescription(event.getDescription());
        dto.setEventDate(event.getEventDate());
        dto.setStatus(event.getStatus());

        if (event.getOrganizer() != null) {
            dto.setOrganizerName(
                event.getOrganizer().getOrganizerName());
        }

        if (event.getVenue() != null) {
            dto.setVenueName(
                event.getVenue().getVenueName());
        }

        return dto;
    }

    public void updateEntity(
            Event event, EventRequestDTO dto) {

        event.setEventName(dto.getEventName());
        event.setDescription(dto.getDescription());
        event.setEventDate(dto.getEventDate());
    }
}