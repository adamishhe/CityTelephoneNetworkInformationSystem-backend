package ru.adamishhe.citytelephonenetworkinformationsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.Street;

@Repository
public interface StreetRepository extends JpaRepository<Street, Long> {
}
