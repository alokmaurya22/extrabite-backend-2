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
public class DonationExpiryScheduler {

    private final DonationRepository donationRepository;

    private static boolean timerExpirySchedulerEnabled = true;

    public static void enableScheduler() {
        timerExpirySchedulerEnabled = true;
    }

    public static void disableScheduler() {
        timerExpirySchedulerEnabled = false;
    }

    public static boolean isSchedulerEnabled() {
        return timerExpirySchedulerEnabled;
    }

    // Run every 10 minutes
    @Scheduled(cron = "0 */10 * * * ?")
    public void expirePreCookedDonations() {
        if (!timerExpirySchedulerEnabled)
            return;
        List<Donation> donations = donationRepository.findAll();
        LocalDateTime now = LocalDateTime.now();
        for (Donation donation : donations) {
            if (donation.getStatus() == DonationStatus.AVAILABLE
                    && Boolean.TRUE.equals(donation.getTimer())
                    && donation.getCreatedDateTime().plusSeconds(donation.getCountdownTime()).isBefore(now)) {
                donation.setStatus(DonationStatus.EXPIRED);
                donationRepository.save(donation);
            }
        }
    }
}