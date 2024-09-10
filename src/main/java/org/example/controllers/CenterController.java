package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.pojo.dto.card.CenterCardDto;
import org.example.pojo.dto.create.CenterCreateDto;
import org.example.pojo.dto.table.CenterTableDto;
import org.example.pojo.dto.update.CenterUpdateDto;
import org.example.services.CenterService;
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
public class CenterController {
    private final CenterService centerService;

    @GetMapping("/center")
    public ResponseEntity<List<CenterTableDto>> getCenterList() {
        return ResponseEntity.ok(centerService.getCenterList());
    }

    @PostMapping("/center")
    public ResponseEntity<Long> addCenter(@RequestBody CenterCreateDto centerCreateDto) {
        return ResponseEntity.ok(centerService.addCenter(centerCreateDto));
    }

    @DeleteMapping("/center/{id}")
    public ResponseEntity<Long> deleteCenter(@PathVariable Long id) {
        return ResponseEntity.ok(centerService.deleteCenter(id));
    }

    @GetMapping("/center/{id}")
    public ResponseEntity<CenterCardDto> getCenterCard(@PathVariable Long id) {
        return ResponseEntity.ok(centerService.getCenterCard(id));
    }

    @PatchMapping("/center")
    public HttpStatus updateCenter(@RequestBody List<CenterUpdateDto> dtoLst) {
        centerService.update(dtoLst);
        return HttpStatus.ACCEPTED;
    }
}
