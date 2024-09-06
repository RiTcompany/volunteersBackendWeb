package org.example.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.exceptions.CenterNotFoundException;
import org.example.mapper.CenterMapper;
import org.example.pojo.dto.card.CenterCardDto;
import org.example.pojo.dto.create.CenterCreateDto;
import org.example.pojo.dto.table.CenterTableDto;
import org.example.repositories.CenterRepository;
import org.example.services.CenterService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CenterServiceImpl implements CenterService {
    private final CenterRepository centerRepository;
    private final CenterMapper centerMapper;

    @Override
    public List<CenterTableDto> getCenterList() {
        return centerRepository.findAll().stream().map(centerMapper::centerDto).toList();
    }

    @Override
    public long addCenter(CenterCreateDto centerCreateDto) {
        return centerRepository.saveAndFlush(centerMapper.center(centerCreateDto)).getId();
    }

    @Override
    public long deleteCenter(Long id) {
        centerRepository.deleteById(id);
        return id;
    }

    @Override
    public CenterCardDto getCenterCard(Long id) {
        return centerRepository.findById(id).map(centerMapper::centerCardDto)
                .orElseThrow(() -> new CenterNotFoundException(id.toString()));
    }
}
