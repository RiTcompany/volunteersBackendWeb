package org.example.mapper;

import org.example.entities.Document;
import org.example.pojo.dto.create.DocumentCreateDto;
import org.example.pojo.dto.table.DocumentTableDto;
import org.example.pojo.dto.update.DocumentUpdateDto;
import org.springframework.stereotype.Component;

@Component
public class DocumentMapper {
    public DocumentTableDto documentDto(Document document) {
        DocumentTableDto dto = new DocumentTableDto();
        dto.setId(document.getId());
        dto.setName(document.getName());
        dto.setSender(document.getSender());
        dto.setRecipient(document.getRecipient());
        dto.setCreateDate(document.getCreateDate());
        dto.setApprovalControl(document.isApprovalControl());
        return dto;
    }

    public Document document(Document document, DocumentUpdateDto updateDto) {
        if (updateDto.getApprovalControl() != null) {
            document.setApprovalControl(updateDto.getApprovalControl());
        }

        return document;
    }

    public Document centerDocument(Long id, DocumentCreateDto dto) {
        Document document = document(dto);
        document.setCenterId(id);
        return document;
    }

    public Document headquartersDocument(Long id, DocumentCreateDto dto) {
        Document document = document(dto);
        document.setHeadquartersId(id);
        return document;
    }

    public Document districtDocument(Long id, DocumentCreateDto dto) {
        Document document = document(dto);
        document.setDistrictTeamId(id);
        return document;
    }

    public Document document(DocumentCreateDto dto) {
        Document document = new Document();
        document.setName(dto.getName());
        document.setSender(dto.getSender());
        document.setRecipient(dto.getRecipient());
        document.setCreateDate(dto.getCreateDate());
        document.setApprovalControl(dto.isApprovalControl());
        return document;
    }
}
