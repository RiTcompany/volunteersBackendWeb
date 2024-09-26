package org.example.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.entities.Equipment;
import org.example.mapper.EquipmentMapper;
import org.example.pojo.dto.create.EquipmentCreateDto;
import org.example.pojo.dto.table.EquipmentTableDto;
import org.example.pojo.filters.EquipmentFilter;
import org.example.repositories.EquipmentRepository;
import org.example.services.EquipmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class EquipmentServiceImpl implements EquipmentService {
    private final EquipmentRepository equipmentRepository;
    private final EquipmentMapper equipmentMapper;


    @Override
    public List<EquipmentTableDto> getEquipmentList(EquipmentFilter filter) {
        Stream<Equipment> stream;
        if (filter.getCenterId() != null) {
            stream = equipmentRepository.findAllByCenterId(filter.getCenterId()).stream();
        } else if (filter.getHeadquartersId() != null) {
            stream = equipmentRepository.findAllByHeadquartersId(filter.getHeadquartersId()).stream();
        } else {
            stream = equipmentRepository.findAll().stream();
        }

        stream = filterByType(stream, filter.getTypeList());
        return stream.map(equipmentMapper::equipmentDto).toList();
    }

    @Override
    public long addCenterEquipment(long centerId, EquipmentCreateDto dto) {
        return equipmentRepository.saveAndFlush(equipmentMapper.centerEquipment(centerId, dto)).getId();
    }

    @Override
    public long addHeadquartersEquipment(long headquartersId, EquipmentCreateDto dto) {
        return equipmentRepository.saveAndFlush(equipmentMapper.headquartersEquipment(headquartersId, dto)).getId();
    }

    @Override
    public long deleteEquipment(Long id) {
        equipmentRepository.deleteById(id);
        return id;
    }

    @Override
    public List<String> getEquipmentypeList() {
        return equipmentRepository.findAllTypes();
    }

    private Stream<Equipment> filterByType(Stream<Equipment> stream, List<String> typeList) {
        if (typeList != null) {
            return stream.filter(equipment -> typeList.contains(equipment.getType()));
        }

        return stream;
    }
}
