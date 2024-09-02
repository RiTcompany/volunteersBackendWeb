package org.example.mapper;

import org.example.entities.Event;
import org.example.pojo.dto.table.EventDto;
import org.example.pojo.dto.update.EventUpdateDto;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {
    public EventDto eventDto(Event event) {
        EventDto eventDto = new EventDto();
        eventDto.setId(event.getId());
        eventDto.setName(event.getName());
        eventDto.setStartTime(event.getStartTime());
        eventDto.setEndTime(event.getEndTime());
        eventDto.setLocation(event.getLocation());
        eventDto.setTeamLeader(event.getTeamLeader());
        return eventDto;
    }

    public Event event(EventDto eventDto) {
        Event event = new Event();
        event.setName(eventDto.getName());
        event.setStartTime(eventDto.getStartTime());
        event.setEndTime(eventDto.getEndTime());
        event.setLocation(eventDto.getLocation());
        event.setTeamLeader(eventDto.getTeamLeader());
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
}
