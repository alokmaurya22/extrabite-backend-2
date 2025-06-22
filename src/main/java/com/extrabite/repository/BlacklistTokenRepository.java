package com.extrabite.repository;

import com.extrabite.entity.BlacklistToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.query.Param;

import java.time.Instant;

public interface BlacklistTokenRepository extends JpaRepository<BlacklistToken, Long> {

    @Query("SELECT COUNT(b) > 0 FROM BlacklistToken b WHERE TRIM(LOWER(b.token)) = TRIM(LOWER(:token))")
    boolean existsByToken(@Param("token") String token);

    // 🆕 Add this method for deleting expired tokens
    @Modifying
    @Transactional
    @Query("DELETE FROM BlacklistToken b WHERE b.expiry <= :now")
    void deleteAllExpiredSince(@Param("now") Instant now);

}