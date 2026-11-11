package com.example.eventmanagement.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.eventmanagement.entity.Event;

public interface EventRepository
        extends JpaRepository<Event, Long> {

    // Derived Query
    List<Event> findByEventNameContainingIgnoreCase(
            String eventName);

    List<Event> findByStatus(String status);

    // JPQL Query
    @Query("SELECT e FROM Event e WHERE e.eventDate >= :date")
    List<Event> findUpcomingEvents(
            @Param("date") LocalDate date);

    // JPQL with pagination
    @Query("SELECT e FROM Event e WHERE e.status = :status")
    Page<Event> findEventsByStatus(
            @Param("status") String status,
            Pageable pageable);

    // Native SQL Query
    @Query(
        value = "SELECT * FROM events WHERE status = ?1",
        nativeQuery = true
    )
    List<Event> findByStatusNative(String status);

    // Named Query is declared on the Event entity
    List<Event> findByEventName(String eventName);

}