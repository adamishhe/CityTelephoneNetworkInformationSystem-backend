package ru.adamishhe.citytelephonenetworkinformationsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.CallLog;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.CallLogId;

@Repository
public interface CallLogRepository extends JpaRepository<CallLog, CallLogId> {

}
