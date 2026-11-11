package com.example.eventmanagement.security;

import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.example.eventmanagement.entity.Organizer;
import com.example.eventmanagement.repository.OrganizerRepository;

import java.util.List;

@Service
public class CustomUserDetailsService
        implements UserDetailsService {

    private final OrganizerRepository repository;

    public CustomUserDetailsService(
            OrganizerRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Organizer organizer = repository.findByEmail(email)
                .orElseThrow(() ->
                    new UsernameNotFoundException(
                        "Organizer not found"));

        return new User(
                organizer.getEmail(),
                organizer.getPassword(),
                List.of(
                    new SimpleGrantedAuthority(
                        "ROLE_" + organizer.getRole())
                )
        );
    }
}