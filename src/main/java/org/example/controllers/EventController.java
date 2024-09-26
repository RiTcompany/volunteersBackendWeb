package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.pojo.dto.card.EventCardDto;
import org.example.pojo.dto.create.EventCreateDto;
import org.example.pojo.dto.table.EventTableDto;
import org.example.pojo.dto.update.EventUpdateDto;
import org.example.services.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @GetMapping("/event")
    public ResponseEntity<List<EventTableDto>> getEventList() {
        return ResponseEntity.ok(eventService.getEventList());
    }

    @GetMapping("/event/{centerId}")
    public ResponseEntity<List<EventTableDto>> getCenterEventList(@PathVariable Long centerId) {
        return ResponseEntity.ok(eventService.getCenterEventList(centerId));
    }

    @GetMapping("/event/{headquartersId}")
    public ResponseEntity<List<EventTableDto>> getHeadquartersIdEventList(@PathVariable Long headquartersId) {
        return ResponseEntity.ok(eventService.getHeadquartersEventList(headquartersId));
    }

    @PostMapping("/event")
    public ResponseEntity<Long> addEvent(@RequestBody EventCreateDto eventCreateDto) {
        return ResponseEntity.ok(eventService.addEvent(eventCreateDto));
    }

    @PatchMapping("/event")
    public HttpStatus updateEvent(@RequestBody List<EventUpdateDto> updateDtoList) {
        eventService.updateEvent(updateDtoList);
        return HttpStatus.ACCEPTED;
    }

    @DeleteMapping("/event/{id}")
    public ResponseEntity<Long> deleteEvent(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.deleteEvent(id));
    }

    @GetMapping("/event/{id}")
    public ResponseEntity<EventCardDto> getEventCard(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEventCardDto(id));
    }
}
