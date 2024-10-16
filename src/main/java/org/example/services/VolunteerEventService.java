package org.example.services;

import org.springframework.stereotype.Service;

@Service
public interface VolunteerEventService {
    void markVolunteerPresence(Long volunteerId, Long eventId);
}
