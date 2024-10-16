package org.example.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.entities.VolunteerEvent;
import org.example.repositories.VolunteerEventRepository;
import org.example.services.VolunteerEventService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VolunteerEventServiceImpl implements VolunteerEventService {
    private final VolunteerEventRepository volunteerEventRepository;

    @Override
    public void markVolunteerPresence(Long volunteerId, Long eventId) {
        VolunteerEvent volunteerEvent = volunteerEventRepository.findByVolunteerIdAndEventId(volunteerId, eventId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Не существует пары волонтер ID = %d и мероприятие ID = %s".formatted(volunteerId, eventId)
                ));
        volunteerEvent.setIsHere(true);
        volunteerEventRepository.saveAndFlush(volunteerEvent);
    }
}
