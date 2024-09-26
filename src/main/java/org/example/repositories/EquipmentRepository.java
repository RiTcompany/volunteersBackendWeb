package org.example.repositories;

import org.example.entities.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    List<Equipment> findAllByCenterId(long centerId);

    List<Equipment> findAllByHeadquartersId(long headquartersId);

    @Query(value = "select type from equipment group by type", nativeQuery = true)
    List<String> findAllTypes();
}
