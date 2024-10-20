package org.example.mapper;

import lombok.RequiredArgsConstructor;
import org.example.entities.Center;
import org.example.exceptions.VolunteerNotFoundException;
import org.example.pojo.dto.card.CenterCardDto;
import org.example.pojo.dto.create.CenterCreateDto;
import org.example.pojo.dto.table.CenterTableDto;
import org.example.pojo.dto.update.CenterUpdateDto;
import org.example.repositories.VolunteerRepository;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class CenterMapper {
    private final VolunteerRepository volunteerRepository;
    private final LinkMapper linkMapper;

    public CenterTableDto centerDto(Center center) {
        CenterTableDto dto = new CenterTableDto();
        dto.setId(center.getId());
        dto.setName(center.getName());
        dto.setParticipantCount(volunteerRepository.countAllByCenterId(center.getId()));
        dto.setLocation(center.getLocation());
        dto.setContact(center.getContact());
        dto.setTeamLeader(linkMapper.participant(center.getTeamLeader()));
        return dto;
    }

    public Center center(CenterCreateDto dto) {
        Center center = new Center();
        center.setName(dto.getName());
        center.setLocation(dto.getLocation());
        center.setContact(dto.getContact());
        center.setFederalId(dto.getFederalId());
        center.setRank(dto.getRank());
        center.setCreateDate(new Date());
        center.setTeamLeader(volunteerRepository.findByVolunteerId(dto.getTeamLeaderVolunteerId()).orElseThrow(() ->
                new VolunteerNotFoundException(dto.getTeamLeaderVolunteerId().toString())
        ));
        return center;
    }

    public CenterCardDto centerCardDto(Center center) {
        CenterCardDto dto = new CenterCardDto();
        dto.setName(center.getName());
        dto.setFederalId(center.getFederalId());
        dto.setRank(center.getRank());
        dto.setCreateDate(center.getCreateDate());
        dto.setParticipantLinkList(
                volunteerRepository.findAllByHeadquartersId(center.getId()).stream()
                        .filter(volunteer -> volunteer.getVolunteerId() != null)
                        .map(linkMapper::participant).toList()
        );
//        dto.setTgLinkList();
//        dto.setVkLinkList();
//        dto.setEquipmentLinkList();
        dto.setDocumentLinkList(center.getDocumentList().stream().map(linkMapper::document).toList());
        dto.setEventLinkList(center.getEventList().stream().map(linkMapper::event).toList());
        return dto;
    }

    public Center center(Center center, CenterUpdateDto dto) {
        if (dto.getName() != null) {
            center.setName(dto.getName());
        }

        if (dto.getContact() != null) {
            center.setContact(dto.getContact());
        }

        if (dto.getLocation() != null) {
            center.setLocation(dto.getLocation());
        }

        return center;
    }
}
