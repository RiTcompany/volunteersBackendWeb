package org.example.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.exceptions.HeadquartersNotFoundException;
import org.example.mapper.HeadquartersMapper;
import org.example.pojo.dto.card.HeadquartersCardDto;
import org.example.pojo.dto.create.HeadquartersCreateDto;
import org.example.pojo.dto.table.HeadquartersTableDto;
import org.example.repositories.HeadquartersRepository;
import org.example.services.HeadquartersService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HeadquartersServiceImpl implements HeadquartersService {
    private final HeadquartersRepository headquartersRepository;
    private final HeadquartersMapper headquartersMapper;

    @Override
    public List<HeadquartersTableDto> getHeadquartersList() {
        return headquartersRepository.findAll().stream().map(headquartersMapper::headquartersDto).toList();
    }

    @Override
    public long addHeadquarters(HeadquartersCreateDto headquartersCreateDto) {
        return headquartersRepository.saveAndFlush(headquartersMapper.headquarters(headquartersCreateDto)).getId();
    }

    @Override
    public long deleteHeadquarters(Long id) {
        headquartersRepository.deleteById(id);
        return id;
    }

    @Override
    public HeadquartersCardDto getHeadquartersCard(Long id) {
        return headquartersRepository.findById(id).map(headquartersMapper::headquartersCardDto)
                .orElseThrow(() -> new HeadquartersNotFoundException(id.toString()));
    }
}
