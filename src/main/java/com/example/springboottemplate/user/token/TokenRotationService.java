package com.example.springboottemplate.user.token;

import com.example.springboottemplate.common.security.JwtService;
import com.example.springboottemplate.user.common.RefreshToken;
import com.example.springboottemplate.user.common.RefreshTokenRepository;
import com.example.springboottemplate.user.common.User;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class TokenRotationService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;

    @Transactional
    public TokenResponse execute(TokenRotationCommand command) {
        UUID refreshTokenId = jwtService.getRefreshTokenId(command.refreshToken());
        RefreshToken refreshToken = refreshTokenRepository
                .findById(refreshTokenId)
                .orElseThrow(() -> {
                    log.warn("Refresh token with id {} not found", refreshTokenId);
                    return new TokenRotationException(Reason.REFRESH_TOKEN_NOT_FOUND);
                });

        User user = refreshToken.getUser();

        if (refreshToken.getRevoked()) {
            log.warn(
                    "Refresh token {} revoked for user {}",
                    refreshToken.getId(),
                    refreshToken.getUser().getId());
            throw new TokenRotationException(Reason.REFRESH_TOKEN_REVOKED);
        }

        if (refreshToken.getExpiresAt().isBefore(Instant.now())) {
            log.warn(
                    "Refresh token {} expired for user {}",
                    refreshToken.getId(),
                    refreshToken.getUser().getId());
            throw new TokenRotationException(Reason.REFRESH_TOKEN_EXPIRED);
        }

        if (refreshToken.getUser().getLocked()) {
            log.warn(
                    "Refresh token cannot be generated for user [{},{}] as it is locked by admin",
                    user.getId(),
                    user.getEmail());
            throw new TokenRotationException(Reason.USER_LOCKED);
        }

        refreshToken.setRevoked(Boolean.TRUE);
        refreshTokenRepository.save(refreshToken);
        log.info("Refresh token {} revoked", refreshTokenId);

        RefreshToken newRefreshToken =
                refreshTokenRepository.save(new RefreshToken(user, jwtService.getRefreshTokenDurationInSeconds()));
        log.info(
                "Refresh token {} generated for user {} email {}",
                newRefreshToken.getId(),
                user.getId(),
                user.getEmail());

        return new TokenResponse(
                jwtService.generateAccessToken(user), jwtService.generateRefreshToken(user, newRefreshToken.getId()));
    }
}
