package com.extrabite.scheduler;

import com.extrabite.entity.Donation;
import com.extrabite.entity.DonationStatus;
import com.extrabite.repository.DonationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DonationExpiryByDateTimeScheduler {

    private final DonationRepository donationRepository;

    private static boolean expiryDateTimeSchedulerEnabled = true;

    public static void enableScheduler() {
        expiryDateTimeSchedulerEnabled = true;
    }

    public static void disableScheduler() {
        expiryDateTimeSchedulerEnabled = false;
    }

    public static boolean isSchedulerEnabled() {
        return expiryDateTimeSchedulerEnabled;
    }

    // Run every 10 minutes
    @Scheduled(cron = "0 */10 * * * ?")
    public void expireDonationsByExpiryDateTime() {
        if (!expiryDateTimeSchedulerEnabled)
            return;
        List<Donation> donations = donationRepository.findAll();
        LocalDateTime now = LocalDateTime.now();
        for (Donation donation : donations) {
            if (donation.getExpiryDateTime() != null && donation.getExpiryDateTime().isBefore(now)) {
                donation.setStatus(DonationStatus.EXPIRED_BY_EXP_TIME);
                donationRepository.save(donation);
            }
        }
    }
}