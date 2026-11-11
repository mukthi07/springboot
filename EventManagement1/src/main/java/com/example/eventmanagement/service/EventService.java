package com.example.eventmanagement.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.eventmanagement.dto.*;
import com.example.eventmanagement.entity.*;
import com.example.eventmanagement.exception.ResourceNotFoundException;
import com.example.eventmanagement.mapper.EventMapper;
import com.example.eventmanagement.repository.*;

@Service
public class EventService {

    private static final Logger logger =
            LoggerFactory.getLogger(EventService.class);

    private final EventRepository eventRepository;
    private final OrganizerRepository organizerRepository;
    private final VenueRepository venueRepository;
    private final EventMapper eventMapper;

    public EventService(
            EventRepository eventRepository,
            OrganizerRepository organizerRepository,
            VenueRepository venueRepository,
            EventMapper eventMapper) {

        this.eventRepository = eventRepository;
        this.organizerRepository = organizerRepository;
        this.venueRepository = venueRepository;
        this.eventMapper = eventMapper;
    }

    // CREATE EVENT
    @Transactional
    @CacheEvict(value = "events", allEntries = true)
    public EventResponseDTO createEvent(EventRequestDTO dto) {

        logger.info("Creating new event: {}", dto.getEventName());

        Organizer organizer = organizerRepository
                .findById(dto.getOrganizerId())
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Organizer not found"));

        Venue venue = venueRepository
                .findById(dto.getVenueId())
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Venue not found"));

        Event event = eventMapper.toEntity(dto);

        event.setOrganizer(organizer);
        event.setVenue(venue);

        Event savedEvent = eventRepository.save(event);

        logger.info("Event created with ID: {}",
                savedEvent.getEventId());

        return eventMapper.toResponseDTO(savedEvent);
    }

    // GET ALL EVENTS
    @Cacheable(value = "events", key = "'all'")
    public List<EventResponseDTO> getAllEvents() {

        logger.info("Fetching all events");

        return eventRepository.findAll()
                .stream()
                .map(eventMapper::toResponseDTO)
                .toList();
    }

    // GET EVENT BY ID
    @Cacheable(value = "events", key = "#id")
    public EventResponseDTO getEventById(Long id) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Event not found with ID: " + id));

        return eventMapper.toResponseDTO(event);
    }

    // UPDATE EVENT
    @Transactional
    @CacheEvict(value = "events", allEntries = true)
    public EventResponseDTO updateEvent(
            Long id, EventRequestDTO dto) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Event not found"));

        Organizer organizer = organizerRepository
                .findById(dto.getOrganizerId())
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Organizer not found"));

        Venue venue = venueRepository
                .findById(dto.getVenueId())
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Venue not found"));

        eventMapper.updateEntity(event, dto);

        event.setOrganizer(organizer);
        event.setVenue(venue);

        return eventMapper.toResponseDTO(
                eventRepository.save(event));
    }

    // DELETE EVENT
    @Transactional
    @CacheEvict(value = "events", allEntries = true)
    public void deleteEvent(Long id) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Event not found"));

        eventRepository.delete(event);

        logger.info("Deleted event ID: {}", id);
    }

    // PAGINATION AND SORTING
    public Page<EventResponseDTO> getEventsWithPagination(
            int page, int size, String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return eventRepository.findAll(pageable)
                .map(eventMapper::toResponseDTO);
    }

    // SEARCH EVENT
    public List<EventResponseDTO> searchEvents(String name) {

        return eventRepository
                .findByEventNameContainingIgnoreCase(name)
                .stream()
                .map(eventMapper::toResponseDTO)
                .toList();
    }
}