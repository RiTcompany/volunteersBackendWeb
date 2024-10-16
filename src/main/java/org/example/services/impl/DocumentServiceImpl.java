package org.example.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.entities.Document;
import org.example.exceptions.DocumentNotFoundException;
import org.example.mapper.DocumentMapper;
import org.example.pojo.dto.create.DocumentCreateDto;
import org.example.pojo.dto.table.DocumentTableDto;
import org.example.pojo.dto.update.DocumentUpdateDto;
import org.example.pojo.filters.DocumentFilter;
import org.example.repositories.DocumentRepository;
import org.example.services.DocumentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    @Value("${media.path}")
    private String PATH_TO_MEDIA;

    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;

    @Override
    public List<DocumentTableDto> getCenterDocumentList(Long centerId, DocumentFilter filter) {
        Stream<Document> stream = documentRepository.findAllByCenterId(centerId).stream();
        stream = sortedStream(
                filterStream(stream, filter), filter
        );
        return stream.map(documentMapper::documentDto).toList();
    }

    @Override
    public List<DocumentTableDto> getHeadquartersDocumentList(Long headquartersId, DocumentFilter filter) {
        Stream<Document> stream = documentRepository.findAllByHeadquartersId(headquartersId).stream();
        stream = sortedStream(
                filterStream(stream, filter), filter
        );
        return stream.map(documentMapper::documentDto).toList();
    }

    @Override
    public List<DocumentTableDto> getDistrictTeamDocumentList(Long districtTeamId, DocumentFilter filter) {
        Stream<Document> stream = documentRepository.findAllByDistrictTeamId(districtTeamId).stream();
        stream = sortedStream(
                filterStream(stream, filter), filter
        );
        return stream.map(documentMapper::documentDto).toList();
    }

    @Override
    public void updateDocument(List<DocumentUpdateDto> updateDtoList) {
        updateDtoList.forEach(updateDto -> {
            Long id = updateDto.getId();
            ;
            Document document = documentRepository.findById(id)
                    .orElseThrow(() -> new DocumentNotFoundException(id.toString()));
            document = documentMapper.document(document, updateDto);
            documentRepository.saveAndFlush(document);
        });
    }

    @Override
    public Long addCenterDocument(Long id, DocumentCreateDto documentCreateDto, MultipartFile multipartFile) throws IOException {
        Document document = documentMapper.centerDocument(id, documentCreateDto);
        return saveFile(multipartFile, document);
    }

    @Override
    public Long addHeadquartersDocument(Long id, DocumentCreateDto documentCreateDto, MultipartFile multipartFile) throws IOException {
        Document document = documentMapper.headquartersDocument(id, documentCreateDto);
        return saveFile(multipartFile, document);
    }

    @Override
    public Long addDistrictDocument(Long id, DocumentCreateDto documentCreateDto) throws IOException {
        Document document = documentMapper.districtDocument(id, documentCreateDto);
        return saveFile(documentCreateDto.getMultipartFile(), document);
    }

    @Override
    public void delete(Long id) {
        documentRepository.deleteById(id);
    }

    @Override
    public InputStream getFile(Long id) throws FileNotFoundException {
        Document document = documentRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Не существует документа ID = ".concat(String.valueOf(id)))
        );
        File file = new File(document.getFilePath());
        return new FileInputStream(file);
    }

    private Long saveFile(MultipartFile multipartFile, Document document) throws IOException {
        Files.createDirectories(Paths.get(PATH_TO_MEDIA + "/document/"));
        File file = new File(PATH_TO_MEDIA + "/document/" + multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);
        document.setFilePath(file.getAbsolutePath());
        return documentRepository.saveAndFlush(document).getId();
    }

    private Stream<Document> filterStream(Stream<Document> stream, DocumentFilter filter) {
        return filterByEndDate(
                filterByStartDate(
                        filterByApprovalControl(
                                stream, filter.getApprovalControl()
                        ), filter.getStartDate()
                ), filter.getEndDate()
        );
    }

    private Stream<Document> sortedStream(Stream<Document> stream, DocumentFilter filter) {
        return sortByDateDesc(
                sortByDateAsc(
                        stream, filter.isOrderByDateAsc()
                ), filter.isOrderByDateDesc()
        );
    }

    private Stream<Document> filterByApprovalControl(Stream<Document> stream, Boolean approvalControl) {
        if (approvalControl != null) {
            stream = stream.filter(document ->
                    approvalControl.equals(document.isApprovalControl())
            );
        }

        return stream;
    }

    private Stream<Document> filterByStartDate(Stream<Document> stream, Date startDate) {
        if (startDate != null) {
            stream = stream.filter(document ->
                    !startDate.after(document.getCreateDate())
            );
        }

        return stream;
    }

    private Stream<Document> filterByEndDate(Stream<Document> stream, Date endDate) {
        if (endDate != null) {
            stream = stream.filter(document ->
                    !endDate.before(document.getCreateDate())
            );
        }

        return stream;
    }

    private Stream<Document> sortByDateAsc(Stream<Document> stream, boolean orderByDateAsc) {
        if (orderByDateAsc) {
            stream = stream.sorted(Comparator.comparing(Document::getCreateDate));
        }

        return stream;
    }

    private Stream<Document> sortByDateDesc(Stream<Document> stream, boolean orderByDateDesc) {
        if (orderByDateDesc) {
            stream = stream.sorted(Comparator.comparing(Document::getCreateDate).reversed());
        }

        return stream;
    }
}
