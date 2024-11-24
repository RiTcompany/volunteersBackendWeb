package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.pojo.dto.card.HeadquartersCardDto;
import org.example.pojo.dto.create.HeadquartersCreateDto;
import org.example.pojo.dto.table.HeadquartersTableDto;
import org.example.pojo.dto.update.HeadquartersUpdateDto;
import org.example.services.HeadquartersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class HeadquartersController {
    private final HeadquartersService headquartersService;

    @GetMapping("/headquarters")
    public ResponseEntity<List<HeadquartersTableDto>> getHeadquartersList() {
        return ResponseEntity.ok(headquartersService.getHeadquartersList());
    }

    @PostMapping("/headquarters")
    public ResponseEntity<Long> addHeadquarters(@RequestBody HeadquartersCreateDto headquartersCreateDto) {
        return ResponseEntity.ok(headquartersService.addHeadquarters(headquartersCreateDto));
    }

    @DeleteMapping("/headquarters/{id}")
    public ResponseEntity<Long> deleteHeadquarters(@PathVariable Long id) {
        return ResponseEntity.ok(headquartersService.deleteHeadquarters(id));
    }

    @GetMapping("/headquarters/{id}")
    public ResponseEntity<HeadquartersCardDto> getHeadquartersCard(@PathVariable Long id) {
        return ResponseEntity.ok(headquartersService.getHeadquartersCard(id));
    }

    @PatchMapping("/headquarters")
    public HttpStatus updateCenter(@RequestBody List<HeadquartersUpdateDto> dtoList) {
        headquartersService.update(dtoList);
        return HttpStatus.ACCEPTED;
    }
}
