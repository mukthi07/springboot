package com.example.eventmanagement.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.example.eventmanagement.dto.EventRequestDTO;
import com.example.eventmanagement.dto.EventResponseDTO;
import com.example.eventmanagement.service.EventService;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    // CREATE EVENT
    @PostMapping
    public ResponseEntity<EventResponseDTO> createEvent(
            @Valid @RequestBody EventRequestDTO dto) {

        EventResponseDTO response =
                eventService.createEvent(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // GET ALL EVENTS
    @GetMapping
    public ResponseEntity<List<EventResponseDTO>> getAllEvents() {

        return ResponseEntity.ok(
                eventService.getAllEvents());
    }

    // GET EVENT BY ID
    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDTO> getEventById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                eventService.getEventById(id));
    }

    // UPDATE EVENT
    @PutMapping("/{id}")
    public ResponseEntity<EventResponseDTO> updateEvent(
            @PathVariable Long id,
            @Valid @RequestBody EventRequestDTO dto) {

        return ResponseEntity.ok(
                eventService.updateEvent(id, dto));
    }

    // DELETE EVENT
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(
            @PathVariable Long id) {

        eventService.deleteEvent(id);

        return ResponseEntity.ok(
                "Event deleted successfully");
    }

    // PAGINATION AND SORTING
    @GetMapping("/page")
    public ResponseEntity<Page<EventResponseDTO>> getEventsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "eventName") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        return ResponseEntity.ok(
                eventService.getEventsWithPagination(
                        page, size, sortBy, direction));
    }

    // SEARCH
    @GetMapping("/search")
    public ResponseEntity<List<EventResponseDTO>> searchEvents(
            @RequestParam String name) {

        return ResponseEntity.ok(
                eventService.searchEvents(name));
    }
}