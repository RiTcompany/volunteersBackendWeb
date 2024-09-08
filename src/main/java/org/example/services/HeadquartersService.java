package org.example.services;

import org.example.pojo.dto.card.HeadquartersCardDto;
import org.example.pojo.dto.create.HeadquartersCreateDto;
import org.example.pojo.dto.table.HeadquartersTableDto;
import org.example.pojo.dto.update.HeadquartersUpdateDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HeadquartersService {
    List<HeadquartersTableDto> getHeadquartersList();

    long addHeadquarters(HeadquartersCreateDto headquartersCreateDto);

    long deleteHeadquarters(Long id);

    HeadquartersCardDto getHeadquartersCard(Long id);

    void update(Long id, HeadquartersUpdateDto dto);
}
