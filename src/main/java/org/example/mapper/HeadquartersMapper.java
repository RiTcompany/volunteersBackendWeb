package org.example.mapper;

import lombok.RequiredArgsConstructor;
import org.example.entities.Headquarters;
import org.example.exceptions.VolunteerNotFoundException;
import org.example.pojo.dto.card.HeadquartersCardDto;
import org.example.pojo.dto.create.HeadquartersCreateDto;
import org.example.pojo.dto.table.HeadquartersTableDto;
import org.example.pojo.dto.update.HeadquartersUpdateDto;
import org.example.repositories.VolunteerRepository;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class HeadquartersMapper {
    private final VolunteerRepository volunteerRepository;
    private final LinkMapper linkMapper;

    public HeadquartersTableDto headquartersDto(Headquarters headquarters) {
        HeadquartersTableDto dto = new HeadquartersTableDto();
        dto.setId(headquarters.getId());
        dto.setName(headquarters.getName());
        dto.setParticipantCount(volunteerRepository.countAllByHeadquartersId(headquarters.getId()));
        dto.setLocation(headquarters.getLocation());
        dto.setContact(headquarters.getContact());
        dto.setTeamLeader(linkMapper.participant(headquarters.getTeamLeader()));
        return dto;
    }

    public Headquarters headquarters(HeadquartersCreateDto dto) {
        Headquarters headquarters = new Headquarters();
        headquarters.setName(dto.getName());
        headquarters.setLocation(dto.getLocation());
        headquarters.setContact(dto.getContact());
        headquarters.setFederalId(dto.getFederalId());
        headquarters.setRank(dto.getRank());
        headquarters.setCreateDate(new Date());
        headquarters.setTeamLeader(volunteerRepository.findByVolunteerId(dto.getTeamLeaderVolunteerId()).orElseThrow(() ->
                new VolunteerNotFoundException(dto.getTeamLeaderVolunteerId().toString())
        ));
        return headquarters;
    }

    public HeadquartersCardDto headquartersCardDto(Headquarters headquarters) {
        HeadquartersCardDto dto = new HeadquartersCardDto();
        dto.setName(headquarters.getName());
        dto.setFederalId(headquarters.getFederalId());
        dto.setRank(headquarters.getRank());
        dto.setCreateDate(headquarters.getCreateDate());
        dto.setParticipantLinkList(
                volunteerRepository.findAllByHeadquartersId(headquarters.getId()).stream()
                        .filter(volunteer -> volunteer.getVolunteerId() != null)
                        .map(linkMapper::participant).toList()
        );
//        dto.setTgLinkList();
//        dto.setVkLinkList();
//        dto.setEquipmentLinkList();
        dto.setDocumentLinkList(headquarters.getDocumentList().stream().map(linkMapper::document).toList());
        dto.setEventLinkList(headquarters.getEventList().stream().map(linkMapper::event).toList());
        return dto;
    }

    public Headquarters headquarters(Headquarters headquarters, HeadquartersUpdateDto dto) {
        if (dto.getName() != null) {
            headquarters.setName(dto.getName());
        }

        if (dto.getContact() != null) {
            headquarters.setContact(dto.getContact());
        }

        if (dto.getLocation() != null) {
            headquarters.setLocation(dto.getLocation());
        }

        return headquarters;
    }
}
