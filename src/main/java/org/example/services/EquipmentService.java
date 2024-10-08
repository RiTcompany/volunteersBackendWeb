package org.example.services;

import org.example.pojo.dto.create.EquipmentCreateDto;
import org.example.pojo.dto.table.EquipmentTableDto;
import org.example.pojo.filters.EquipmentFilter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EquipmentService {
    List<EquipmentTableDto> getEquipmentList(EquipmentFilter filter);

    long addCenterEquipment(long centerId, EquipmentCreateDto equipmentTableDto);

    long addHeadquartersEquipment(long headquartersId, EquipmentCreateDto equipmentTableDto);

    long deleteEquipment(Long id);

    List<String> getEquipmentypeList();
}
