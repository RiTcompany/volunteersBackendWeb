package org.example.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.entities.VolunteerEvent;
import org.example.enums.ERole;
import org.example.exceptions.EventNotFoundException;
import org.example.exceptions.PermissionDeniedException;
import org.example.exceptions.VolunteerEquipmentNotFoundException;
import org.example.exceptions.VolunteerNotFoundException;
import org.example.pojo.GetNamesResponse;
import org.example.pojo.MarkEquipmentReturnedRequest;
import org.example.pojo.PresenceMarkRequest;
import org.example.repositories.EventRepository;
import org.example.repositories.UserRepository;
import org.example.repositories.VolunteerEventRepository;
import org.example.repositories.VolunteerRepository;
import org.example.services.VolunteerEventService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VolunteerEventServiceImpl implements VolunteerEventService {

    private final VolunteerEventRepository volunteerEventRepository;
    private final VolunteerRepository volunteerRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    public void markVolunteerPresence(Long volunteerId, Long eventId, PresenceMarkRequest markRequest) {
        var volunteer = volunteerRepository.findByVolunteerId(markRequest.getAdminId())
                .orElseThrow(() -> new VolunteerNotFoundException("Volunteer not found"));
        var user = userRepository.findByEmail(volunteer.getEmail())
                .orElseThrow(() -> new VolunteerNotFoundException("User not found"));
        var event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not found"));
        if (user.getRoleList().isEmpty()) throw new PermissionDeniedException();
        if (!event.getTeamLeader().equals(volunteer.getFullName())) throw new PermissionDeniedException();
        VolunteerEvent volunteerEvent = volunteerEventRepository.findByVolunteerIdAndEventId(volunteerId, eventId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Не существует пары волонтер ID = %d и мероприятие ID = %s".formatted(volunteerId, eventId)
                ));
        volunteerEvent.setIsHere(true);
        if (markRequest.getEquipmentId() != null) {
            volunteerEvent.setAdminId(markRequest.getAdminId());
            volunteerEvent.setEquipmentId(markRequest.getEquipmentId());
            volunteerEvent.setHasEquipmentReturned(true);
        }
        volunteerEventRepository.saveAndFlush(volunteerEvent);
    }

    @Override
    public void markEquipmentReturned(Long volunteerId, MarkEquipmentReturnedRequest request) {
        VolunteerEvent volunteerEvent = volunteerEventRepository.findByVolunteerIdAndEventId(volunteerId, request.getEventId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Не существует пары волонтер ID = %d и мероприятие ID = %s".formatted(volunteerId, request.getEventId())
                ));
        if (volunteerEvent.getAdminId().equals(request.getAdminId()) && volunteerEvent.getEquipmentId().equals(request.getEquipmentId())) {
            volunteerEvent.setHasEquipmentReturned(false);
        } else {
            throw new VolunteerEquipmentNotFoundException(volunteerId, request.getEquipmentId(), request.getAdminId());
        }
        volunteerEventRepository.saveAndFlush(volunteerEvent);
    }

    @Override
    public GetNamesResponse getEventNameAndVolunteerName(Long volunteerId, Long eventId) {
        return GetNamesResponse.builder()
                .volunteerName(volunteerRepository.findVolunteerNameById(volunteerId))
                .eventName(eventRepository.findEventNameById(eventId))
                .build();
    }
}
