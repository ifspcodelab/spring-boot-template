package com.example.springboottemplate.user.login;

import com.example.springboottemplate.common.security.JwtService;
import com.example.springboottemplate.user.common.RefreshToken;
import com.example.springboottemplate.user.common.RefreshTokenRepository;
import com.example.springboottemplate.user.common.User;
import com.example.springboottemplate.user.common.UserRepository;
import com.example.springboottemplate.user.token.TokenResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class LoginService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public TokenResponse execute(LoginCommand command) {
        User user = userRepository.findByEmail(command.email()).orElseThrow(() -> {
            log.warn("User {} not found", command.email());
            return new LoginException(Reason.WRONG_EMAIL_OR_PASSWORD);
        });

        if (!passwordEncoder.matches(command.password(), user.getPassword())) {
            log.warn("User [{}, {}] entered the wrong password", user.getId(), user.getEmail());
            throw new LoginException(Reason.WRONG_EMAIL_OR_PASSWORD);
        }

        // After user is found and the password matches

        if (!user.getEnabled()) {
            log.warn("User [{}, {}] did not confirm verification email", user.getId(), user.getEmail());
            throw new LoginException(Reason.USER_DISABLED);
        }

        if (user.getLocked()) {
            log.warn("User [{}, {}] was locked by admin", user.getId(), user.getEmail());
            throw new LoginException(Reason.USER_LOCKED);
        }

        RefreshToken refreshToken =
                refreshTokenRepository.save(new RefreshToken(user, jwtService.getRefreshTokenDurationInSeconds()));
        log.info(
                "Refresh token id {} generated for user {} email {}",
                refreshToken.getId(),
                user.getId(),
                user.getEmail());

        return new TokenResponse(
                jwtService.generateAccessToken(user), jwtService.generateRefreshToken(user, refreshToken.getId()));
    }
}
