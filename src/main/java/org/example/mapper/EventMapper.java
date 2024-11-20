package org.example.mapper;

import lombok.RequiredArgsConstructor;
import org.example.entities.Event;
import org.example.exceptions.VolunteerNotFoundException;
import org.example.pojo.dto.card.EventCardDto;
import org.example.pojo.dto.create.EventCreateDto;
import org.example.pojo.dto.table.EventTableDto;
import org.example.pojo.dto.update.EventUpdateDto;
import org.example.repositories.VolunteerRepository;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class EventMapper {
    private final VolunteerRepository volunteerRepository;
    private final LinkMapper linkMapper;

    public EventTableDto eventDto(Event event) {
        EventTableDto dto = new EventTableDto();
        dto.setId(event.getId());
        dto.setName(event.getName());
        dto.setStartTime(event.getStartTime());
        dto.setEndTime(event.getEndTime());
        dto.setLocation(event.getLocation());
        dto.setTeamLeader(event.getTeamLeader());
        dto.setRegistrationLink(event.getRegistrationLink());
        dto.setAvailableForRegistration(event.getIsAvailableForRegistration() != null ? event.getIsAvailableForRegistration() : true);
        return dto;
    }

    public Event event(EventCreateDto dto) {
        Event event = new Event();
        event.setName(dto.getName());
        event.setStartTime(dto.getStartTime());
        event.setEndTime(dto.getEndTime());
        event.setLocation(dto.getLocation());
        event.setTeamLeader(dto.getTeamLeader());

        event.setFederalId(dto.getFederalId());
        event.setCreateDate(new Date());
        event.setGroupChatLink(dto.getGroupChatLink());
        event.setSettingParticipantLink(dto.getSettingParticipantLink());
        event.setAnswerableId(
                volunteerRepository.findByVolunteerId(dto.getAnswerableVolunteerId()).orElseThrow(() ->
                        new VolunteerNotFoundException(dto.getAnswerableVolunteerId().toString())
                ).getId()
        );
        event.setRegistrationLink(dto.getRegistrationLink());
        return event;
    }

    public Event event(Event event, EventUpdateDto updateDto) {
        if (updateDto.getName() != null) {
            event.setName(updateDto.getName());
        }

        if (updateDto.getStartTime() != null) {
            event.setStartTime(updateDto.getStartTime());
        }

        if (updateDto.getEndTime() != null) {
            event.setEndTime(updateDto.getEndTime());
        }

        if (updateDto.getLocation() != null) {
            event.setLocation(updateDto.getLocation());
        }

        if (updateDto.getTeamLeader() != null) {
            event.setTeamLeader(updateDto.getTeamLeader());
        }

        return event;
    }

    public EventCardDto eventCardDto(Event event) {
        EventCardDto dto = new EventCardDto();
        dto.setId(event.getFederalId());
        dto.setLocation(event.getLocation());
        dto.setStartDate(event.getStartTime());
        dto.setEndDate(event.getEndTime());
        dto.setCreateDate(new Date());
        dto.setGroupChatLink(event.getGroupChatLink());
        dto.setSettingParticipantLink(event.getSettingParticipantLink());
        dto.setAnswerableLink(
                volunteerRepository.findById(event.getAnswerableId()).map(linkMapper::participant)
                        .orElseThrow(() -> new VolunteerNotFoundException(event.getAnswerableId().toString()))
        );
        dto.setTeamLeadLink(event.getTeamLeader());
        dto.setRegistrationLink(event.getRegistrationLink());
        return dto;
    }
}
