package org.example.repositories;

import org.example.entities.Functional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FunctionalRepository extends JpaRepository<Functional, Long> {
    Optional<Functional> findByEventIdAndParticipialId(Long eventId, Long participialId);
}
