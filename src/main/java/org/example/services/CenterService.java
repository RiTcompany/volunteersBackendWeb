package org.example.services;

import org.example.pojo.dto.card.CenterCardDto;
import org.example.pojo.dto.create.CenterCreateDto;
import org.example.pojo.dto.table.CenterTableDto;
import org.example.pojo.dto.update.CenterUpdateDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CenterService {
    List<CenterTableDto> getCenterList();

    long addCenter(CenterCreateDto centerCreateDto);

    long deleteCenter(Long id);

    CenterCardDto getCenterCard(Long id);

    void update(Long id, List<CenterUpdateDto> dto);
}
