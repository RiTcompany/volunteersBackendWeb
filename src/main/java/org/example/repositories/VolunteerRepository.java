package org.example.repositories;

import org.example.entities.Event;
import org.example.entities.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
    List<Volunteer> findAllByDistrictTeamId(long districtTeamId);

    List<Volunteer> findAllByCenterId(long centerId);

    List<Volunteer> findAllByHeadquartersId(long headquartersId);

    List<Volunteer> findAllByEventListContains(Event event);

    int countAllByCenterId(long centerId);

    int countAllByHeadquartersId(long headquartersId);

    Optional<Volunteer> findByVolunteerId(Long volunteerId);
}
