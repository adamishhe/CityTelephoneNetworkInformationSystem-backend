package ru.adamishhe.citytelephonenetworkinformationsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

}
