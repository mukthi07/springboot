package com.example.eventmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@EnableTransactionManagement
public class EventManagement1Application {

    public static void main(String[] args) {

        SpringApplication.run(
            EventManagement1Application.class, args);

        System.out.println(
            "Event Management System Started Successfully!");
    }
}