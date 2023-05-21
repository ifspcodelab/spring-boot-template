package com.example.springboottemplate.user.common.recaptcha;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@AllArgsConstructor
@Slf4j
public class RecaptchaFilter extends OncePerRequestFilter {
    private final RecaptchaService recaptchaService;
    private final RecaptchaConfig recaptchaConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Check if recaptcha is enabled
        // Disabled is necessary to test endpoints without a frontend
        if (!recaptchaConfig.enabled()) {
            filterChain.doFilter(request, response);
            return;
        }

        log.info("Recaptcha filter was called");

        var urlForFilter = List.of("/api/v1/registrations", "/api/v1/login", "/api/v1/password-reset");

        if (urlForFilter.contains(request.getRequestURI())) {
            String recaptcha = request.getHeader("recaptcha");

            if (recaptcha == null || recaptcha.isBlank()) {
                throw new BadCredentialsException("Recaptcha header empty or null");
            }

            RecaptchaResponse recaptchaResponse = recaptchaService.validateToken(recaptcha);
            if (!recaptchaResponse.success() || recaptchaResponse.score() < recaptchaConfig.scoreThreshold()) {
                log.warn("Invalid reCaptcha token");
                log.warn("{}", recaptchaResponse);
                throw new BadCredentialsException("Invalid reCaptcha token");
            }
        }

        filterChain.doFilter(request, response);
    }
}
