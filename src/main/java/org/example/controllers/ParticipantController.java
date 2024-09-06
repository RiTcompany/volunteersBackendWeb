package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.pojo.dto.card.PersonalAccountDto;
import org.example.pojo.dto.table.CenterParticipantTableDto;
import org.example.pojo.dto.table.DistrictParticipantTableDto;
import org.example.pojo.dto.table.EventParticipantTableDto;
import org.example.pojo.dto.table.VolunteerTableDto;
import org.example.pojo.dto.update.ParticipantUpdateDto;
import org.example.pojo.filters.ParticipantFilter;
import org.example.services.ParticipantService;
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
public class ParticipantController {
    private final ParticipantService participantService;

    @PostMapping("/volunteer")
    public ResponseEntity<List<VolunteerTableDto>> getVolunteerList(@RequestBody ParticipantFilter filter) {
        return ResponseEntity.ok(participantService.getVolunteerList(filter));
    }

    @PatchMapping("/volunteer")
    public HttpStatus updateVolunteer(
            @RequestBody List<ParticipantUpdateDto> updateDtoList
    ) {
        participantService.updateParticipant(updateDtoList);
        return HttpStatus.ACCEPTED;
    }

    @DeleteMapping("/volunteer/{id}")
    public ResponseEntity<Long> deleteVolunteer(@PathVariable Long id) {
        return ResponseEntity.ok(participantService.deleteVolunteer(id));
    }

    @PostMapping("/center_participant/{centerId}")
    public ResponseEntity<List<CenterParticipantTableDto>> getCenterParticipantList(
            @PathVariable Long centerId, @RequestBody ParticipantFilter filter
    ) {
        return ResponseEntity.ok(participantService.getCenterParticipantList(centerId, filter));
    }

    @PostMapping("/event_participant/{eventId}")
    public ResponseEntity<List<EventParticipantTableDto>> getEventParticipantList(
            @PathVariable Long eventId, @RequestBody ParticipantFilter filter
    ) {
        return ResponseEntity.ok(participantService.getEventParticipantList(eventId, filter));
    }

    @PostMapping("/district_team_participant/{districtTeamId}")
    public ResponseEntity<List<DistrictParticipantTableDto>> getDistrictParticipantList(
            @PathVariable Long districtTeamId, @RequestBody ParticipantFilter filter
    ) {
        return ResponseEntity.ok(participantService.getDistrictParticipantList(districtTeamId, filter));
    }

    @GetMapping("/personal_account/{id}")
    public ResponseEntity<PersonalAccountDto> getPersonalAccountCard(@PathVariable Long id) {
        return ResponseEntity.ok(participantService.getPersonalAccount(id));
    }
}