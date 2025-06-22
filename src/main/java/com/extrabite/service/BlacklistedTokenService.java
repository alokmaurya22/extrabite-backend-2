package com.extrabite.service;

import com.extrabite.entity.BlacklistToken;
import com.extrabite.repository.BlacklistTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class BlacklistedTokenService {

    private final BlacklistTokenRepository blacklistTokenRepository;

    /**
     * Saves the token to the blacklist repository with expiry.
     */
    public void blacklistToken(String token) {
        if (!blacklistTokenRepository.existsByToken(token)) {
            BlacklistToken blacklisted = BlacklistToken.builder()
                    .token(token.trim())
                    .expiry(Instant.now().plusSeconds(86400)) // 🔒 Token valid for 1 day more
                    .build();

            blacklistTokenRepository.save(blacklisted);
        }
    }

    /**
     * Check whether a token is blacklisted.
     */
    public boolean isTokenBlacklisted(String token) {
        return blacklistTokenRepository.existsByToken(token.trim());
    }
}