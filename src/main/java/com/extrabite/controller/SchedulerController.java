package com.extrabite.controller;

import com.extrabite.scheduler.DonationExpiryScheduler;
import com.extrabite.scheduler.DonationExpiryByDateTimeScheduler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/scheduler")
public class SchedulerController {

    @PostMapping("/timer-expiry/enable")
    public ResponseEntity<?> enableTimerExpiryScheduler() {
        DonationExpiryScheduler.enableScheduler();
        return ResponseEntity.ok("Timer expiry scheduler enabled");
    }

    @PostMapping("/timer-expiry/disable")
    public ResponseEntity<?> disableTimerExpiryScheduler() {
        DonationExpiryScheduler.disableScheduler();
        return ResponseEntity.ok("Timer expiry scheduler disabled");
    }

    @PostMapping("/expiry-date-time/enable")
    public ResponseEntity<?> enableExpiryDateTimeScheduler() {
        DonationExpiryByDateTimeScheduler.enableScheduler();
        return ResponseEntity.ok("ExpiryDateTime scheduler enabled");
    }

    @PostMapping("/expiry-date-time/disable")
    public ResponseEntity<?> disableExpiryDateTimeScheduler() {
        DonationExpiryByDateTimeScheduler.disableScheduler();
        return ResponseEntity.ok("ExpiryDateTime scheduler disabled");
    }

    @GetMapping("/status")
    public ResponseEntity<?> getSchedulersStatus() {
        Map<String, Boolean> status = new HashMap<>();
        status.put("timerExpirySchedulerEnabled", DonationExpiryScheduler.isSchedulerEnabled());
        status.put("expiryDateTimeSchedulerEnabled", DonationExpiryByDateTimeScheduler.isSchedulerEnabled());
        return ResponseEntity.ok(status);
    }
}