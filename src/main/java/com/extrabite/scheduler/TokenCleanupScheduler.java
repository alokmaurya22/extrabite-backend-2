package com.extrabite.scheduler;

import com.extrabite.repository.BlacklistTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class TokenCleanupScheduler {

    private final BlacklistTokenRepository blacklistTokenRepository;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    //  Runs every hour
    @Scheduled(cron = "0 0 * * * ?")
    public void cleanExpiredTokens() {
        Instant now = Instant.now();
        Instant cutoff = now.minusMillis(jwtExpiration);

        System.out.println("🧹 Cleaning tokens expired before: " + cutoff);

        blacklistTokenRepository.deleteAllExpiredSince(cutoff);
    }
}