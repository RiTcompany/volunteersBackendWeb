package org.example.services;

import org.example.pojo.dto.table.DocumentDto;
import org.example.pojo.dto.update.DocumentUpdateDto;
import org.example.pojo.filters.DocumentFilter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DocumentService {
    List<DocumentDto> getCenterDocumentList(Long centerId, DocumentFilter filter);

    List<DocumentDto> getDistrictTeamDocumentList(Long districtTeamId, DocumentFilter filter);

    void updateDocument(List<DocumentUpdateDto> updateDtoList);

    Long addDocument(DocumentDto documentDto);
}
