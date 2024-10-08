package org.example.repositories;

import org.example.entities.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findAllByCenterId(long centerId);

    List<Document> findAllByHeadquartersId(long headquartersId);

    List<Document> findAllByDistrictTeamId(long districtTeamId);
}
