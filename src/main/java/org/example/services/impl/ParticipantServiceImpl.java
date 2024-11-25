package org.example.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.entities.Event;
import org.example.entities.Volunteer;
import org.example.enums.EColor;
import org.example.exceptions.VolunteerNotFoundException;
import org.example.mapper.ParticipialMapper;
import org.example.pojo.dto.card.PersonalAccountDto;
import org.example.pojo.dto.table.CenterParticipantTableDto;
import org.example.pojo.dto.table.DistrictParticipantTableDto;
import org.example.pojo.dto.table.EventParticipantTableDto;
import org.example.pojo.dto.table.HeadquartersParticipantTableDto;
import org.example.pojo.dto.table.VolunteerTableDto;
import org.example.pojo.dto.update.DistrictTeamParticipantUpdateDto;
import org.example.pojo.dto.update.ParticipantUpdateDto;
import org.example.pojo.filters.ParticipantFilter;
import org.example.repositories.EventRepository;
import org.example.repositories.UserRepository;
import org.example.repositories.VolunteerRepository;
import org.example.services.ParticipantService;
import org.example.utils.DateUtil;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {
    private final VolunteerRepository volunteerRepository;
    private final ParticipialMapper participialMapper;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    public List<VolunteerTableDto> getVolunteerList(ParticipantFilter filter) {
        Stream<Volunteer> stream = volunteerRepository.findAll().stream()
                .filter(volunteer -> volunteer.getVolunteerId() != null);

        stream = filterByMinAge(stream, filter.getMinAge());
        stream = filterByMaxAge(stream, filter.getMaxAge());
        stream = filterByMinRank(stream, filter.getMinRank());

        stream = filterByColor(stream, filter.getColorList());
        stream = filterByEvent(stream, filter.getEventIdList());
        stream = filterByLevel(stream, filter.getLevelList());
        stream = filterByInterview(stream, filter.getHasInterview());
        stream = filterByCenter(stream, filter.getCenterIdList());
        stream = filterByHeadquarters(stream, filter.getHeadquartersIdList());

        stream = sortByRank(stream, filter);
        stream = sortByDate(stream, filter);

        return stream.map(participialMapper::volunteerDto).toList();
    }

    @Override
    public void updateParticipant(List<ParticipantUpdateDto> updateDtoList) throws VolunteerNotFoundException {
        updateDtoList.forEach(updateDto -> {
            Long id = updateDto.getId();
            Volunteer volunteer = volunteerRepository.findById(id)
                    .orElseThrow(() -> new VolunteerNotFoundException(id.toString()));
            volunteer = participialMapper.volunteer(volunteer, updateDto);
            volunteerRepository.saveAndFlush(volunteer);
        });
    }

    @Override
    public Long deleteVolunteer(Long id) {
        volunteerRepository.deleteById(id);
        return id;
    }

    @Override
    public List<DistrictParticipantTableDto> getDistrictParticipantList(Long districtTeamId, ParticipantFilter filter) {
        Stream<Volunteer> stream = volunteerRepository.findAllByDistrictTeamId(districtTeamId).stream()
                .filter(volunteer -> volunteer.getVolunteerId() != null);

        stream = filterByMinAge(stream, filter.getMinAge());
        stream = filterByMaxAge(stream, filter.getMaxAge());

        stream = filterByColor(stream, filter.getColorList());
        stream = filterByEvent(stream, filter.getEventIdList());

        stream = sortByDate(stream, filter);

        return stream.map(participialMapper::districtParticipantDto).toList();
    }

    @Override
    public List<EventParticipantTableDto> getEventParticipantList(Long eventId, ParticipantFilter filter) {
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new EntityNotFoundException("Не существует Event ID = ".concat(String.valueOf(eventId)))
        );
        Stream<Volunteer> stream = volunteerRepository.findAllByEventListContains(event).stream()
                .filter(volunteer -> volunteer.getVolunteerId() != null);

        stream = filterByMinAge(stream, filter.getMinAge());
        stream = filterByMaxAge(stream, filter.getMaxAge());
        stream = filterByMinRank(stream, filter.getMinRank());
//        TODO : filter by functional
        stream = filterByTesting(stream, filter.getTesting());
        stream = filterByClothes(stream, filter.getHasClothes());

        stream = sortByRank(stream, filter);
        stream = sortByDate(stream, filter);

        return stream.map(volunteer -> participialMapper.eventParticipantDto(volunteer, eventId))
                .toList();
    }

    @Override
    public List<CenterParticipantTableDto> getCenterParticipantList(Long centerId, ParticipantFilter filter) {
        Stream<Volunteer> stream = volunteerRepository.findAllByCenterId(centerId).stream()
                .filter(volunteer -> volunteer.getVolunteerId() != null);

        stream = filterByMinAge(stream, filter.getMinAge());
        stream = filterByMaxAge(stream, filter.getMaxAge());
        stream = filterByMinRank(stream, filter.getMinRank());
        stream = filterByInterview(stream, filter.getHasInterview());
        stream = filterByLevel(stream, filter.getLevelList());

        stream = filterByColor(stream, filter.getColorList());
        stream = filterByEvent(stream, filter.getEventIdList());

        stream = sortByRank(stream, filter);
        stream = sortByDate(stream, filter);

        return stream.map(participialMapper::centerParticipantDto).toList();
    }

    @Override
    public List<HeadquartersParticipantTableDto> getHeadquartersParticipantList(Long headquartersId, ParticipantFilter filter) {
        Stream<Volunteer> stream = volunteerRepository.findAllByHeadquartersId(headquartersId).stream()
                .filter(volunteer -> volunteer.getVolunteerId() != null);

        stream = filterByMinAge(stream, filter.getMinAge());
        stream = filterByMaxAge(stream, filter.getMaxAge());
        stream = filterByMinRank(stream, filter.getMinRank());
        stream = filterByInterview(stream, filter.getHasInterview());
        stream = filterByLevel(stream, filter.getLevelList());

        stream = filterByColor(stream, filter.getColorList());
        stream = filterByEvent(stream, filter.getEventIdList());

        stream = sortByRank(stream, filter);
        stream = sortByDate(stream, filter);

        return stream.map(participialMapper::headquartersParticipantDto).toList();
    }

    @Override
    public void changeDistrictTeamForParticipant(DistrictTeamParticipantUpdateDto dto) {
        Volunteer volunteer = volunteerRepository.findByVolunteerId(dto.getVolunteerId()).orElseThrow(() ->
                new EntityNotFoundException("Не сущетвует volunteer ID = ".concat(String.valueOf(dto.getVolunteerId())))
        );
        volunteer.setDistrictTeamId(dto.getDistrictTeamId());
        volunteerRepository.saveAndFlush(volunteer);
    }

    @Override
    public PersonalAccountDto getPersonalAccount(Long id) {
        var u = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Shit"));
        return volunteerRepository.findByEmail(u.getEmail()).map(participialMapper::personalAccountDto)
                .orElseThrow(() -> new VolunteerNotFoundException(id.toString()));
    }

    private Stream<Volunteer> sortByRank(Stream<Volunteer> stream, ParticipantFilter filter) {
        return sortByRankDesc(
                sortByRankAsc(
                        stream, filter.isOrderByRankAsc()
                ), filter.isOrderByRankDesc()
        );
    }

    private Stream<Volunteer> sortByDate(Stream<Volunteer> stream, ParticipantFilter filter) {
        return sortByDateDesc(
                sortByDateAsc(
                        stream, filter.isOrderByDateAsc()
                ), filter.isOrderByDateDesc()
        );
    }

    private Stream<Volunteer> filterByMinAge(Stream<Volunteer> stream, Integer minAge) {
        if (minAge != null) {
            return stream.filter(volunteer ->
                    minAge <= DateUtil.age(volunteer.getBirthday())
            );
        }

        return stream;
    }

    private Stream<Volunteer> filterByMaxAge(Stream<Volunteer> stream, Integer maxAge) {
        if (maxAge != null) {
            return stream.filter(volunteer ->
                    maxAge >= DateUtil.age(volunteer.getBirthday())
            );
        }

        return stream;
    }

    private Stream<Volunteer> filterByMinRank(Stream<Volunteer> stream, Double minRank) {
        if (minRank != null) {
            stream = stream.filter(volunteer ->
                    volunteer.getRank() != null && minRank <= volunteer.getRank()
            );
        }

        return stream;
    }

    private Stream<Volunteer> filterByColor(Stream<Volunteer> stream, List<EColor> colorList) {
        if (colorList != null) {
            stream = stream.filter(volunteer ->
                    colorList.contains(volunteer.getColor())
            );
        }

        return stream;
    }

    private Stream<Volunteer> filterByEvent(Stream<Volunteer> stream, List<Long> eventIdList) {
        if (eventIdList != null) {
            stream = stream.filter(volunteer ->
                    volunteer.getEventList().stream()
                            .map(Event::getId)
                            .collect(Collectors.toSet())
                            .containsAll(eventIdList)
            );
        }

        return stream;
    }

    private Stream<Volunteer> filterByLevel(Stream<Volunteer> stream, List<String> levelList) {
        if (levelList != null) {
            stream = stream.filter(volunteer ->
                    levelList.contains(volunteer.getLevel())
            );
        }

        return stream;
    }

    private Stream<Volunteer> filterByCenter(Stream<Volunteer> stream, List<Long> centerIdList) {
        if (centerIdList != null) {
            stream = stream.filter(volunteer ->
                    centerIdList.contains(volunteer.getCenter().getId())
            );
        }

        return stream;
    }

    private Stream<Volunteer> filterByHeadquarters(Stream<Volunteer> stream, List<Long> headquartersIdList) {
        if (headquartersIdList != null) {
            stream = stream.filter(volunteer ->
                    headquartersIdList.contains(volunteer.getHeadquarters().getId())
            );
        }

        return stream;
    }

    private Stream<Volunteer> sortByRankAsc(Stream<Volunteer> stream, boolean orderByRankAsc) {
        if (orderByRankAsc) {
            stream = stream.sorted(Comparator.comparing(Volunteer::getRank));
        }

        return stream;
    }

    private Stream<Volunteer> sortByRankDesc(Stream<Volunteer> stream, boolean orderByRankDesc) {
        if (orderByRankDesc) {
            stream = stream.sorted(Comparator.comparing(Volunteer::getRank).reversed());
        }

        return stream;
    }

    private Stream<Volunteer> sortByDateAsc(Stream<Volunteer> stream, boolean orderByDateAsc) {
        if (orderByDateAsc) {
            stream = stream.sorted(Comparator.comparing(Volunteer::getBirthday));
        }

        return stream;
    }

    private Stream<Volunteer> sortByDateDesc(Stream<Volunteer> stream, boolean orderByDateDesc) {
        if (orderByDateDesc) {
            stream = stream.sorted(Comparator.comparing(Volunteer::getBirthday).reversed());
        }

        return stream;
    }

    private Stream<Volunteer> filterByTesting(Stream<Volunteer> stream, Boolean testing) {
        if (testing != null) {
            stream = stream.filter(volunteer ->
                    testing.equals(volunteer.isTesting())
            );
        }

        return stream;
    }

    private Stream<Volunteer> filterByClothes(Stream<Volunteer> stream, Boolean hasClothes) {
        if (hasClothes != null) {
            stream = stream.filter(volunteer ->
                    hasClothes.equals(volunteer.getHasAnorak())
            );
        }

        return stream;
    }

    private Stream<Volunteer> filterByInterview(Stream<Volunteer> stream, Boolean interview) {
        if (interview != null) {
            stream = stream.filter(volunteer ->
                    interview.equals(volunteer.isHasInterview())
            );
        }

        return stream;
    }
}
