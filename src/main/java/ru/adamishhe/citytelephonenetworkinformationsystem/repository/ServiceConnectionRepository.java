package ru.adamishhe.citytelephonenetworkinformationsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.ServiceConnection;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.ServiceConnectionId;

@Repository
public interface ServiceConnectionRepository extends JpaRepository<ServiceConnection, ServiceConnectionId> {
}
