package com.example.springboottemplate.user.password;

import com.example.springboottemplate.user.common.User;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetRepository extends JpaRepository<PasswordReset, UUID> {
    boolean existsByUserAndUsedFalseAndExpiresOnAfter(User user, Instant expireOn);

    Optional<PasswordReset> findByToken(UUID token);
}
