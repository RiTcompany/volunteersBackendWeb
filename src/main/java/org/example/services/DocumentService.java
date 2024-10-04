package org.example.services;

import org.example.pojo.dto.create.DocumentCreateDto;
import org.example.pojo.dto.table.DocumentTableDto;
import org.example.pojo.dto.update.DocumentUpdateDto;
import org.example.pojo.filters.DocumentFilter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DocumentService {
    List<DocumentTableDto> getCenterDocumentList(Long centerId, DocumentFilter filter);

    List<DocumentTableDto> getHeadquartersDocumentList(Long headquartersId, DocumentFilter filter);

    List<DocumentTableDto> getDistrictTeamDocumentList(Long districtTeamId, DocumentFilter filter);

    void updateDocument(List<DocumentUpdateDto> updateDtoList);

    Long addCenterDocument(Long id, DocumentCreateDto documentCreateDto);

    Long addHeadquartersDocument(Long id, DocumentCreateDto documentCreateDto);

    Long addDistrictDocument(Long id, DocumentCreateDto documentCreateDto);

    void delete(Long id);
}
