package org.example.services;

import org.example.pojo.GetNamesResponse;
import org.example.pojo.MarkEquipmentReturnedRequest;
import org.example.pojo.PresenceMarkRequest;
import org.springframework.stereotype.Service;

@Service
public interface VolunteerEventService {
    void markVolunteerPresence(Long volunteerId, Long eventId, PresenceMarkRequest markRequest);

    void markEquipmentReturned(Long volunteerId, MarkEquipmentReturnedRequest request);

    GetNamesResponse getEventNameAndVolunteerName(Long volunteerId, Long eventId);
}
