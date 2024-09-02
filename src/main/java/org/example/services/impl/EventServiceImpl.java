package org.example.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.entities.Event;
import org.example.exceptions.EventNotFoundException;
import org.example.mapper.EventMapper;
import org.example.pojo.dto.table.EventDto;
import org.example.pojo.dto.update.EventUpdateDto;
import org.example.repositories.EventRepository;
import org.example.services.EventService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public List<EventDto> getEventList() {
        return eventRepository.findAll().stream().map(eventMapper::eventDto).toList();
    }

    @Override
    public List<EventDto> getCenterEventList(Long centerId) {
        return eventRepository.findAllByCenterId(centerId).stream().map(eventMapper::eventDto).toList();
    }

    @Override
    public Long addEvent(EventDto eventDto) {
        return eventRepository.saveAndFlush(eventMapper.event(eventDto)).getId();
    }

    @Override
    public void updateEvent(List<EventUpdateDto> updateDtoList) {
        updateDtoList.forEach(updateDto -> {
            Long id = updateDto.getId();
            Event event = eventRepository.findById(id)
                    .orElseThrow(() -> new EventNotFoundException(id.toString()));
            event = eventMapper.event(event, updateDto);
            eventRepository.saveAndFlush(event);
        });
    }

    @Override
    public Long deleteEvent(Long id) {
        eventRepository.deleteById(id);
        return id;
    }
}
