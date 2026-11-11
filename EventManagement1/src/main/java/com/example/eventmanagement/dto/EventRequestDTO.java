package com.example.eventmanagement.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.*;

public class EventRequestDTO {

    @NotBlank(message = "Event name is required")
    private String eventName;

    private String description;

    @NotNull(message = "Event date is required")
    @FutureOrPresent(message = "Event date cannot be in the past")
    private LocalDate eventDate;

    @NotNull(message = "Organizer ID is required")
    @Positive(message = "Organizer ID must be positive")
    private Long organizerId;

    @NotNull(message = "Venue ID is required")
    @Positive(message = "Venue ID must be positive")
    private Long venueId;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public Long getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(Long organizerId) {
        this.organizerId = organizerId;
    }

    public Long getVenueId() {
        return venueId;
    }

    public void setVenueId(Long venueId) {
        this.venueId = venueId;
    }
}