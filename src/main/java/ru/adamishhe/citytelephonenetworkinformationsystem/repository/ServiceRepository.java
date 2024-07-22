package ru.adamishhe.citytelephonenetworkinformationsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.ServiceS;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceS, Long> {

}