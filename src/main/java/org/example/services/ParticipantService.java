package org.example.services;

import org.example.pojo.dto.card.PersonalAccountDto;
import org.example.pojo.dto.table.*;
import org.example.pojo.dto.update.ParticipantUpdateDto;
import org.example.pojo.filters.ParticipantFilter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ParticipantService {
    List<VolunteerTableDto> getVolunteerList(ParticipantFilter filter);

    void updateParticipant(List<ParticipantUpdateDto> updateDtoList);

    Long deleteVolunteer(Long id);

    List<DistrictParticipantTableDto> getDistrictParticipantList(Long districtTeamId, ParticipantFilter filter);

    List<EventParticipantTableDto> getEventParticipantList(Long eventId, ParticipantFilter filter);

    List<CenterParticipantTableDto> getCenterParticipantList(Long centerId, ParticipantFilter filter);

    List<HeadquartersParticipantTableDto> getHeadquartersParticipantList(Long headquartersId, ParticipantFilter filter);

    PersonalAccountDto getPersonalAccount(Long id);
}
