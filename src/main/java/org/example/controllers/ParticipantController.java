package org.example.controllers;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import lombok.RequiredArgsConstructor;
import org.example.pojo.dto.card.PersonalAccountDto;
import org.example.pojo.dto.table.CenterParticipantTableDto;
import org.example.pojo.dto.table.DistrictParticipantTableDto;
import org.example.pojo.dto.table.EventParticipantTableDto;
import org.example.pojo.dto.table.HeadquartersParticipantTableDto;
import org.example.pojo.dto.table.VolunteerTableDto;
import org.example.pojo.dto.update.DistrictTeamParticipantUpdateDto;
import org.example.pojo.dto.update.ParticipantUpdateDto;
import org.example.pojo.filters.ParticipantFilter;
import org.example.services.ParticipantService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
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

    @PostMapping("/headquarters_participant/{headquartersId}")
    public ResponseEntity<List<HeadquartersParticipantTableDto>> getHeadquartersParticipantList(
            @PathVariable Long headquartersId, @RequestBody ParticipantFilter filter
    ) {
        return ResponseEntity.ok(participantService.getHeadquartersParticipantList(headquartersId, filter));
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

    @PatchMapping("/district_team_participant/change")
    public HttpStatus getDistrictParticipantList(@RequestBody DistrictTeamParticipantUpdateDto dto) {
        participantService.changeDistrictTeamForParticipant(dto);
        return HttpStatus.ACCEPTED;
    }

    @GetMapping("/personal_account/{id}")
    public ResponseEntity<PersonalAccountDto> getPersonalAccountCard(@PathVariable Long id) {
        return ResponseEntity.ok(participantService.getPersonalAccount(id));
    }

    //TODO: Скрыть логику в соответствующий сервис и перенести эндпоинт в контроллер, если идейно должен лежать не тут
    @GetMapping("/volunteer/{id}/generateQR")
    public ResponseEntity<byte[]> generateQRCode(@PathVariable Long id) {
        try {
            String url = "http://localhost:8082/volunteer/" + id + "/mark";

            BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, 300, 300);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", byteArrayOutputStream);

            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"qrcode.png\"")
                    .contentType(MediaType.IMAGE_PNG)
                    .body(byteArrayOutputStream.toByteArray());

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //TODO: Добавить логику пометки волонтера присутствующим и позволить переходить по ссылке только роли, которая за это в ответе
    @GetMapping("/volunteer/{id}/mark")
    public ResponseEntity<Long> markVolunteerPresence(@PathVariable Long id) {
        //логика по помечанию волонтера присутствующим
        return ResponseEntity.ok(id);
    }

}
