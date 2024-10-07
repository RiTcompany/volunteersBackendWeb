package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.pojo.dto.create.DocumentCreateDto;
import org.example.pojo.dto.table.DocumentTableDto;
import org.example.pojo.filters.DocumentFilter;
import org.example.services.DocumentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;

    @PostMapping("/center_document/{centerId}")
    public ResponseEntity<List<DocumentTableDto>> getCenterDocumentList(
            @PathVariable Long centerId, @RequestBody DocumentFilter filter
    ) {
        return ResponseEntity.ok(documentService.getCenterDocumentList(centerId, filter));
    }

    @PostMapping("/headquarters_document/{headquartersId}")
    public ResponseEntity<List<DocumentTableDto>> getHeadquartersDocumentList(
            @PathVariable Long headquartersId, @RequestBody DocumentFilter filter
    ) {
        return ResponseEntity.ok(documentService.getHeadquartersDocumentList(headquartersId, filter));
    }

    @PostMapping("/district_team_document/{districtTeamId}")
    public ResponseEntity<List<DocumentTableDto>> getDistrictTeamDocumentList(
            @PathVariable Long districtTeamId, @RequestBody DocumentFilter filter
    ) {
        return ResponseEntity.ok(documentService.getDistrictTeamDocumentList(districtTeamId, filter));
    }

//    @PatchMapping("/document")
//    public HttpStatus updateDocument(@RequestBody List<DocumentUpdateDto> updateDtoList) {
//        documentService.updateDocument(updateDtoList);
//        return HttpStatus.ACCEPTED;
//    }

    @PostMapping("/add_center_document/{id}")
    public ResponseEntity<Long> addCenterDocument(
            @RequestParam("file") MultipartFile multipartFile,
            @PathVariable Long id,
            @RequestBody DocumentCreateDto documentCreateDto
    ) throws IOException {
        return ResponseEntity.ok(documentService.addCenterDocument(id, documentCreateDto,multipartFile));
    }

    @PostMapping("/add_headquarters_document/{id}")
    public ResponseEntity<Long> addHeadquartersDocument(
            @RequestParam("file") MultipartFile multipartFile,
            @PathVariable Long id,
            @RequestBody DocumentCreateDto documentCreateDto
    ) throws IOException {
        return ResponseEntity.ok(documentService.addHeadquartersDocument(id, documentCreateDto, multipartFile));
    }

    @PostMapping("/add_district_document/{id}")
    public ResponseEntity<Long> addDistrictDocument(
            @RequestParam("file") MultipartFile multipartFile,
            @PathVariable Long id,
            @RequestBody DocumentCreateDto documentCreateDto
    ) throws IOException {
        return ResponseEntity.ok(documentService.addDistrictDocument(id, documentCreateDto, multipartFile));
    }

    @DeleteMapping("/document/{id}")
    public HttpStatus deleteDocument(@PathVariable Long id) {
        documentService.delete(id);
        return HttpStatus.ACCEPTED;
    }

    @GetMapping("/document/{id}/file")
    public ResponseEntity<InputStreamResource> getFile(@PathVariable Long id) throws FileNotFoundException {
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(new InputStreamResource(documentService.getFile(id)));
    }
}
