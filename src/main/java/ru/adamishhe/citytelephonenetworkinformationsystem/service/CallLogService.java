package ru.adamishhe.citytelephonenetworkinformationsystem.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.CallLog;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.CallLogId;
import ru.adamishhe.citytelephonenetworkinformationsystem.repository.CallLogRepository;

import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class CallLogService {
    private final CallLogRepository callLogRepository;

    public CallLogService(CallLogRepository callLogRepository) {
        this.callLogRepository = callLogRepository;
    }

    public Page<CallLog> getAllCallLogs(Pageable pageable) {
        return callLogRepository.findAll(pageable);
    }

    public CallLog saveCallLog(CallLog callLog) {
        CallLogId callLogId = callLog.getId();
        System.out.println("Saving CallLog with ID: " + callLogId);
        System.out.println("Initiator: " + callLogId.getInitiator());
        System.out.println("Call Time: " + callLogId.getCallTime());

        CallLog savedCallLog = callLogRepository.save(callLog);
        System.out.println("Saved CallLog: " + savedCallLog);
        return savedCallLog;
    }

    public CallLog updateCallLog(Long initiator, ZonedDateTime callTime, CallLog callLog) {
        CallLogId id = new CallLogId(initiator, callTime);
        Optional<CallLog> existingCallLog = callLogRepository.findById(id);

        if (existingCallLog.isPresent()) {
            CallLog updatedCallLog = existingCallLog.get();
            if (callLog.getRecipientNo() != null) {
                updatedCallLog.setRecipientNo(callLog.getRecipientNo());
            }
            if (callLog.getRecipientAtsAddress() != null) {
                updatedCallLog.setRecipientAtsAddress(callLog.getRecipientAtsAddress());
            }
            updatedCallLog.setDuration(callLog.getDuration());

            return callLogRepository.save(updatedCallLog);
        } else {
            throw new RuntimeException("CallLog not found");
        }
    }

    public void deleteCallLog(Long initiator, ZonedDateTime callTime) {
        CallLogId id = new CallLogId(initiator, callTime);
        callLogRepository.deleteById(id);
    }
}
