package org.example.repositories;

import org.example.entities.Headquarters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeadquartersRepository extends JpaRepository<Headquarters, Long> {
}
