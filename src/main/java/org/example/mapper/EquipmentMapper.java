package org.example.mapper;

import org.example.entities.Equipment;
import org.example.pojo.dto.create.EquipmentCreateDto;
import org.example.pojo.dto.table.EquipmentTableDto;
import org.springframework.stereotype.Component;

@Component
public class EquipmentMapper {
    public EquipmentTableDto equipmentDto(Equipment equipment) {
        EquipmentTableDto dto = new EquipmentTableDto();
        dto.setId(equipment.getId());
        dto.setEquipmentId(equipment.getEquipmentId());
        dto.setType(equipment.getType());
        dto.setYear(equipment.getYear());
        dto.setCurrentOwner(equipment.getCurrentOwner());
        return dto;
    }

    public Equipment equipment(EquipmentCreateDto dto) {
        Equipment equipment = new Equipment();
        equipment.setEquipmentId(dto.getEquipmentId());
        equipment.setType(dto.getType());
        equipment.setYear(dto.getYear());
        return equipment;
    }
}
