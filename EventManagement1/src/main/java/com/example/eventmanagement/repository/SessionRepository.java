package com.example.eventmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;

import com.example.eventmanagement.entity.Session;

public interface SessionRepository
        extends JpaRepository<Session, Long> {

    List<Session> findByEventEventId(Long eventId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM Session s WHERE s.sessionId = :id")
    Session findByIdForUpdate(@Param("id") Long id);

}