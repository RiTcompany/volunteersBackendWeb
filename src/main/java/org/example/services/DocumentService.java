package org.example.services;

import org.example.pojo.dto.create.DocumentCreateDto;
import org.example.pojo.dto.table.DocumentTableDto;
import org.example.pojo.dto.update.DocumentUpdateDto;
import org.example.pojo.filters.DocumentFilter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public interface DocumentService {
    List<DocumentTableDto> getCenterDocumentList(Long centerId, DocumentFilter filter);

    List<DocumentTableDto> getHeadquartersDocumentList(Long headquartersId, DocumentFilter filter);

    List<DocumentTableDto> getDistrictTeamDocumentList(Long districtTeamId, DocumentFilter filter);

    void updateDocument(List<DocumentUpdateDto> updateDtoList);

    Long addCenterDocument(Long id, DocumentCreateDto documentCreateDto, MultipartFile multipartFile) throws IOException;

    Long addHeadquartersDocument(Long id, DocumentCreateDto documentCreateDto, MultipartFile multipartFile) throws IOException;

    Long addDistrictDocument(Long id, DocumentCreateDto documentCreateDto) throws IOException;

    void delete(Long id);

    InputStream getFile(Long id) throws FileNotFoundException;
}
