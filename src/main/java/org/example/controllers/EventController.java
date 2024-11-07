package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.entities.Event;
import org.example.pojo.dto.card.EventCardDto;
import org.example.pojo.dto.create.EventCreateDto;
import org.example.pojo.dto.table.EventTableDto;
import org.example.pojo.dto.update.EventUpdateDto;
import org.example.services.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
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

    @PostMapping("/{id}/addTrainingLink")
    public ResponseEntity<Event> addTrainingLink(@PathVariable Long id, @RequestParam String trainingLink) {
        Event updatedEvent = eventService.addTrainingLink(id, trainingLink);
        return ResponseEntity.ok(updatedEvent);
    }

    @PostMapping("/{id}/addResultsLink")
    public ResponseEntity<Event> addResultsLink(@PathVariable Long id, @RequestParam String resultsLink) {
        Event updatedEvent = eventService.addResultsLink(id, resultsLink);
        return ResponseEntity.ok(updatedEvent);
    }

    @GetMapping("/{id}/results")
    public ResponseEntity<Boolean> getResultsByEmail(@PathVariable Long id, @RequestParam String email) throws GeneralSecurityException, IOException {
        Boolean result = eventService.getResultByEmail(id, email);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

}
