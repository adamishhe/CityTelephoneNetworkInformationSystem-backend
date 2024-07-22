package ru.adamishhe.citytelephonenetworkinformationsystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.District;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {
    Page<District> findAll(Pageable pageable);
}
