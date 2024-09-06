package org.example.mapper;

import lombok.RequiredArgsConstructor;
import org.example.entities.Event;
import org.example.entities.Functional;
import org.example.entities.Volunteer;
import org.example.exceptions.CenterNotFoundException;
import org.example.exceptions.HeadquartersNotFoundException;
import org.example.pojo.dto.card.PersonalAccountDto;
import org.example.pojo.dto.table.CenterParticipantTableDto;
import org.example.pojo.dto.table.DistrictParticipantTableDto;
import org.example.pojo.dto.table.EventParticipantTableDto;
import org.example.pojo.dto.LinkDto;
import org.example.pojo.dto.table.VolunteerTableDto;
import org.example.pojo.dto.update.ParticipantUpdateDto;
import org.example.repositories.CenterRepository;
import org.example.repositories.FunctionalRepository;
import org.example.repositories.HeadquartersRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ParticipialMapper {
    private final DateMapper dateMapper;
    private final LinkMapper linkMapper;
    private final CenterRepository centerRepository;
    private final HeadquartersRepository headquartersRepository;
    private final FunctionalRepository functionalRepository;

    public PersonalAccountDto personalAccountDto(Volunteer volunteer) {
        PersonalAccountDto dto = new PersonalAccountDto();
        dto.setVolunteerId(volunteer.getVolunteerId());
        dto.setFullName(volunteer.getFullName());
        dto.setBirthdayDto(dateMapper.birthdayWithAge(volunteer.getBirthday()));
        dto.setRank(volunteer.getRank());
        dto.setTgLink(volunteer.getTgLink());
        dto.setVkLink(volunteer.getVk());
        dto.setCenterLink(linkMapper.center(volunteer.getCenter()));
        dto.setHeadquartersLink(linkMapper.headquarters(volunteer.getHeadquarters()));
        dto.setEventLinkList(volunteer.getEventList().stream().map(linkMapper::event).toList());
        return dto;
    }

    public VolunteerTableDto volunteerDto(Volunteer volunteer) {
        VolunteerTableDto dto = new VolunteerTableDto();
        dto.setVolunteerId(volunteer.getVolunteerId());
        dto.setFullName(volunteer.getFullName());
        dto.setBirthdayDto(dateMapper.birthdayWithAge(volunteer.getBirthday()));
        dto.setTgLink(volunteer.getTgLink());
        dto.setVk(volunteer.getVk());
        dto.setColor(volunteer.getColor().getValue());
        dto.setEventLinkList(eventLinkList(volunteer.getEventList()));
        dto.setComment(volunteer.getComment());
        dto.setRank(volunteer.getRank());
        dto.setHasInterview(volunteer.isHasInterview());
        dto.setLevel(volunteer.getLevel());
        dto.setCenterLink(linkMapper.center(volunteer.getCenter()));
        dto.setHeadquartersLink(linkMapper.headquarters(volunteer.getHeadquarters()));
        return dto;
    }

    public DistrictParticipantTableDto districtParticipantDto(Volunteer volunteer) {
        DistrictParticipantTableDto dto = new DistrictParticipantTableDto();
        dto.setVolunteerId(volunteer.getVolunteerId());
        dto.setFullName(volunteer.getFullName());
        dto.setBirthdayDto(dateMapper.birthdayWithAge(volunteer.getBirthday()));
        dto.setTgLink(volunteer.getTgLink());
        dto.setVkLink(volunteer.getVk());
        dto.setColor(volunteer.getColor().getValue());
        dto.setEventLinkList(eventLinkList(volunteer.getEventList()));
        return dto;
    }

    public EventParticipantTableDto eventParticipantDto(Volunteer volunteer, long eventId) {
        EventParticipantTableDto dto = new EventParticipantTableDto();
        dto.setVolunteerId(volunteer.getVolunteerId());
        dto.setFullName(volunteer.getFullName());
        dto.setBirthdayDto(dateMapper.birthdayWithAge(volunteer.getBirthday()));
        dto.setTgLink(volunteer.getTgLink());
        dto.setFunctional(
                functionalRepository.findByEventIdAndParticipialId(eventId, volunteer.getId())
                        .map(Functional::getName).orElse(null)
        );
        dto.setTesting(volunteer.isTesting());
        dto.setComment(volunteer.getComment());
        dto.setRank(volunteer.getRank());
        dto.setHasClothes(volunteer.getHasAnorak()); // TODO : так ли понял
        return dto;
    }

    public CenterParticipantTableDto centerParticipantDto(Volunteer volunteer) {
        CenterParticipantTableDto dto = new CenterParticipantTableDto();
        dto.setVolunteerId(volunteer.getVolunteerId());
        dto.setFullName(volunteer.getFullName());
        dto.setBirthday(volunteer.getBirthday());
        dto.setTgLink(volunteer.getTgLink());
        dto.setVkLink(volunteer.getVk());
        dto.setColor(volunteer.getColor());
        dto.setRank(volunteer.getRank());
        dto.setInterview(volunteer.isHasInterview());
        dto.setLevel(volunteer.getLevel());
        return dto;
    }

    public Volunteer volunteer(Volunteer volunteer, ParticipantUpdateDto updateDto) {
        if (updateDto.getFullName() != null) {
            volunteer.setFullName(updateDto.getFullName());
        }

        if (updateDto.getBirthday() != null) {
            volunteer.setBirthday(updateDto.getBirthday());
        }

        if (updateDto.getTgLink() != null) {
            volunteer.setTgLink(updateDto.getTgLink());
        }

        if (updateDto.getVkLink() != null) {
            volunteer.setVk(updateDto.getVkLink());
        }

        if (updateDto.getColor() != null) {
            volunteer.setColor(updateDto.getColor());
        }

        if (updateDto.getComment() != null) {
            volunteer.setComment(updateDto.getComment());
        }

        if (updateDto.getHasInterview() != null) {
            volunteer.setHasInterview(updateDto.getHasInterview());
        }

        if (updateDto.getLevel() != null) {
            volunteer.setLevel(updateDto.getLevel());
        }

        if (updateDto.getCenterId() != null) {
            volunteer.setCenter(centerRepository.findById(updateDto.getCenterId())
                    .orElseThrow(() -> new CenterNotFoundException(updateDto.getCenterId().toString()))
            );
        }

        if (updateDto.getHeadquartersId() != null) {
            volunteer.setHeadquarters(headquartersRepository.findById(updateDto.getHeadquartersId())
                    .orElseThrow(() -> new HeadquartersNotFoundException(updateDto.getHeadquartersId().toString()))
            );
        }

        return volunteer;
    }

    private List<LinkDto> eventLinkList(List<Event> eventList) {
        return eventList.stream().map(linkMapper::event).toList();
    }
}
