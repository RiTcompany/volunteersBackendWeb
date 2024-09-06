package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.pojo.dto.table.EquipmentTableDto;
import org.example.pojo.filters.EquipmentFilter;
import org.example.services.EquipmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EquipmentController {
    private final EquipmentService equipmentService;

    @PostMapping("/all_equipment")
    public ResponseEntity<List<EquipmentTableDto>> getEquipmentList(@RequestBody EquipmentFilter filter) {
        return ResponseEntity.ok(equipmentService.getEquipmentList(filter));
    }

    @PostMapping("/equipment")
    public ResponseEntity<Long> addEquipment(@RequestBody EquipmentTableDto equipmentTableDto) {
        return ResponseEntity.ok(equipmentService.addEquipment(equipmentTableDto));
    }

    @DeleteMapping("/equipment/{id}")
    public ResponseEntity<Long> deleteEquipment(@PathVariable Long id) {
        return ResponseEntity.ok(equipmentService.deleteEquipment(id));
    }
}
