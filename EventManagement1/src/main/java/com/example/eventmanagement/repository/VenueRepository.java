package com.example.eventmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eventmanagement.entity.Venue;

public interface VenueRepository
        extends JpaRepository<Venue, Long> {

}