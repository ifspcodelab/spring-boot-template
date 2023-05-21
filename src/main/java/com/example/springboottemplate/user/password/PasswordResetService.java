package com.example.springboottemplate.user.password;

import com.example.springboottemplate.common.config.AppSecurityConfig;
import com.example.springboottemplate.user.common.User;
import com.example.springboottemplate.user.common.UserRepository;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class PasswordResetService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetRepository passwordResetRepository;
    private final AppSecurityConfig appSecurityConfig;

    @Transactional
    public PasswordResetResponse requestReset(PasswordResetCommand command) {
        User user = userRepository
                .findByEmail(command.email())
                .orElseThrow(() -> new PasswordResetException(Reason.USER_NOT_FOUND));

        if (!user.getEnabled()) {
            log.warn("User [{}, {}] did not confirm verification email", user.getId(), user.getEmail());
            throw new PasswordResetException(Reason.USER_DISABLED);
        }

        if (user.getLocked()) {
            log.warn("User [{}, {}] was locked by admin", user.getId(), user.getEmail());
            throw new PasswordResetException(Reason.USER_LOCKED);
        }

        if (passwordResetRepository.existsByUserAndUsedFalseAndExpiresOnAfter(user, Instant.now())) {
            log.warn("Password reset request already exists for user [{}, {}]", user.getId(), user.getEmail());
            throw new PasswordResetException(Reason.PASSWORD_RESET_ALREADY_EXISTS);
        }

        PasswordReset passwordReset = passwordResetRepository.save(
                new PasswordReset(user, appSecurityConfig.passwordResetDurationInSeconds()));
        log.info("Password reset {} generated for user [{}, {}]", passwordReset.getId(), user.getId(), user.getEmail());

        // TODO: send email for user

        return new PasswordResetResponse(
                passwordReset.getId(),
                passwordReset.getCreatedAt(),
                passwordReset.getExpiresOn(),
                passwordReset.getUsed());
    }

    @Transactional
    public PasswordResetResponse changePassword(UUID token, ChangePasswordCommand command) {
        PasswordReset passwordReset = passwordResetRepository
                .findByToken(token)
                .orElseThrow(() -> new PasswordResetException(Reason.PASSWORD_RESET_NOT_FOUND));

        User user = passwordReset.getUser();

        if (passwordReset.getUsed()) {
            log.warn("Password reset {} for user [{}, {}] used", passwordReset.getId(), user.getId(), user.getEmail());
            throw new PasswordResetException(Reason.PASSWORD_RESET_USED);
        }

        if (passwordReset.getExpiresOn().isBefore(Instant.now())) {
            log.warn(
                    "Password reset {} for user [{}, {}] expired",
                    passwordReset.getId(),
                    user.getId(),
                    user.getEmail());
            throw new PasswordResetException(Reason.PASSWORD_RESET_EXPIRED);
        }

        passwordReset.setUsed(Boolean.TRUE);
        passwordResetRepository.save(passwordReset);

        user.setPassword(passwordEncoder.encode(command.password()));
        userRepository.save(user);
        log.info(
                "User [{}, {}] changed his password via password reset {}",
                user.getId(),
                user.getEmail(),
                passwordReset.getId());

        return new PasswordResetResponse(
                passwordReset.getId(),
                passwordReset.getCreatedAt(),
                passwordReset.getExpiresOn(),
                passwordReset.getUsed());
    }
}
