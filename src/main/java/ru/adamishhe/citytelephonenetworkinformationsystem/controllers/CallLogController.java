package ru.adamishhe.citytelephonenetworkinformationsystem.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.CallLog;
import ru.adamishhe.citytelephonenetworkinformationsystem.service.CallLogService;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("/api/call-logs")
public class CallLogController {

    private final CallLogService callLogService;

    public CallLogController(CallLogService callLogService) {
        this.callLogService = callLogService;
    }

    @GetMapping
    public Page<CallLog> getAllCallLogs(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return callLogService.getAllCallLogs(PageRequest.of(page, size));
    }

    @PostMapping
    public ResponseEntity<?> createCallLog(@RequestBody CallLog callLog) {
        try {
            System.out.println("Received CallLog: " + callLog);
            System.out.println("Initiator: " + callLog.getId().getInitiator());
            System.out.println("Call Time: " + callLog.getId().getCallTime());
            System.out.println("Recipient No: " + callLog.getRecipientNo());
            System.out.println("Recipient ATS Address ID: " + callLog.getRecipientAtsAddress().getId());
            System.out.println("Duration: " + callLog.getDuration());

            CallLog savedCallLog = callLogService.saveCallLog(callLog);
            return ResponseEntity.ok(savedCallLog);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{initiator}/{callTime}")
    public CallLog updateCallLog(@PathVariable Long initiator, @PathVariable ZonedDateTime callTime, @RequestBody CallLog callLog) {
        return callLogService.updateCallLog(initiator, callTime, callLog);
    }

    @DeleteMapping("/{initiator}/{callTime}")
    public ResponseEntity<?> deleteCallLog(@PathVariable Long initiator, @PathVariable ZonedDateTime callTime) {
        callLogService.deleteCallLog(initiator, callTime);
        return ResponseEntity.ok().build();
    }
}
