package org.example.repositories;

import org.example.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByCenterId(long centerId);

    List<Event> findAllByHeadquartersId(long headquartersId);

    @Query(value = "select e.name from Event as e where e.id = ?1")
    String findEventNameById(Long eventId);
}
