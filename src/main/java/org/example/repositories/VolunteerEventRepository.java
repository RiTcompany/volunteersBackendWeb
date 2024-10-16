package org.example.repositories;

import org.example.entities.VolunteerEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VolunteerEventRepository extends JpaRepository<VolunteerEvent, Long> {
    Optional<VolunteerEvent> findByVolunteerIdAndEventId(long volunteerId, long eventId);
}
