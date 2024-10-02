package org.example.services.impl;

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
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
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
    public Long addCenterDocument(Long id, DocumentCreateDto documentCreateDto) {
        return documentRepository.saveAndFlush(documentMapper.centerDocument(id, documentCreateDto)).getId();
    }

    @Override
    public Long addHeadquartersDocument(Long id, DocumentCreateDto documentCreateDto) {
        return documentRepository.saveAndFlush(documentMapper.headquartersDocument(id, documentCreateDto)).getId();
    }

    @Override
    public Long addDistrictDocument(Long id, DocumentCreateDto documentCreateDto) {
        return documentRepository.saveAndFlush(documentMapper.districtDocument(id, documentCreateDto)).getId();
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
