package org.example.services;

import org.example.pojo.dto.card.EventCardDto;
import org.example.pojo.dto.create.EventCreateDto;
import org.example.pojo.dto.table.EventTableDto;
import org.example.pojo.dto.update.EventUpdateDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EventService {
    List<EventTableDto> getEventList();

    List<EventTableDto> getCenterEventList(Long centerId);

    List<EventTableDto> getHeadquartersEventList(Long headquartersId);

    Long addEvent(EventCreateDto eventCreateDto);

    void updateEvent(List<EventUpdateDto> updateDtoList);

    Long deleteEvent(Long id);

    EventCardDto getEventCardDto(Long id);
}
