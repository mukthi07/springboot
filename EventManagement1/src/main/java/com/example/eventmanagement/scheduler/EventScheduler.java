package com.example.eventmanagement.scheduler;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.eventmanagement.entity.Event;
import com.example.eventmanagement.repository.EventRepository;

@Component
public class EventScheduler {

    private static final Logger logger =
            LoggerFactory.getLogger(EventScheduler.class);

    private final EventRepository repository;

    public EventScheduler(EventRepository repository) {
        this.repository = repository;
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void updateEventStatus() {

        LocalDate today = LocalDate.now();

        for (Event event : repository.findAll()) {

            if (event.getEventDate().isBefore(today)) {

                event.setStatus("COMPLETED");

            } else {

                event.setStatus("UPCOMING");
            }
        }

        repository.saveAll(repository.findAll());

        logger.info("Event status scheduler executed");
    }
}