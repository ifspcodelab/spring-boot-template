package com.example.springboottemplate.common.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.springboottemplate.common.config.AppSecurityConfig;
import com.example.springboottemplate.user.common.User;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JwtService {
    private final AppSecurityConfig appSecurityConfig;

    public DecodedJWT decodeToken(String token) {
        return JWT.require(getAlgorithm())
                .withIssuer(appSecurityConfig.issuer())
                .build()
                .verify(token);
    }

    public String generateAccessToken(User user) {
        return JWT.create()
                .withIssuer(appSecurityConfig.issuer())
                .withSubject(user.getId().toString())
                .withClaim("username", user.getEmail())
                .withArrayClaim(
                        "roles", user.getRoles().stream().map(Enum::name).toArray(String[]::new))
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusSeconds(getAccessTokenDurationInSeconds()))
                .sign(getAlgorithm());
    }

    public String generateRefreshToken(User user, UUID jwtId) {
        return JWT.create()
                .withIssuer(appSecurityConfig.issuer())
                .withSubject(user.getId().toString())
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusSeconds(getRefreshTokenDurationInSeconds()))
                .withJWTId(jwtId.toString())
                .sign(getAlgorithm());
    }

    public Long getAccessTokenDurationInSeconds() {
        return appSecurityConfig.accessTokenDurationInSeconds();
    }

    public Long getRefreshTokenDurationInSeconds() {
        return appSecurityConfig.refreshTokenDurationInSeconds();
    }

    public UUID getRefreshTokenId(String refreshToken) {
        return UUID.fromString(decodeToken(refreshToken).getId());
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC512(appSecurityConfig.secret());
    }
}
