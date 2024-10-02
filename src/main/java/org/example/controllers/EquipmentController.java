package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.pojo.dto.create.EquipmentCreateDto;
import org.example.pojo.dto.table.EquipmentTableDto;
import org.example.pojo.filters.EquipmentFilter;
import org.example.services.EquipmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EquipmentController {
    private final EquipmentService equipmentService;

    @PostMapping("/equipment")
    public ResponseEntity<List<EquipmentTableDto>> getCenterEquipmentList(@RequestBody EquipmentFilter filter) {
        return ResponseEntity.ok(equipmentService.getEquipmentList(filter));
    }

    @PostMapping("/equipment/{centerId}")
    public ResponseEntity<Long> addCenterEquipment(@PathVariable Long centerId, @RequestBody EquipmentCreateDto dto) {
        return ResponseEntity.ok(equipmentService.addCenterEquipment(centerId, dto));
    }

    @PostMapping("/equipment/{headquartersId}")
    public ResponseEntity<Long> addHeadquartersEquipment(@PathVariable Long headquartersId, @RequestBody EquipmentCreateDto dto) {
        return ResponseEntity.ok(equipmentService.addHeadquartersEquipment(headquartersId, dto));
    }

    @DeleteMapping("/equipment/{id}")
    public ResponseEntity<Long> deleteEquipment(@PathVariable Long id) {
        return ResponseEntity.ok(equipmentService.deleteEquipment(id));
    }

    @GetMapping("/equipment/type_names")
    public ResponseEntity<List<String>> getEquipmentTypeList() {
        return ResponseEntity.ok(equipmentService.getEquipmentypeList());
    }
}
