package com.example.springboottemplate.user.common.recaptcha;

import com.example.springboottemplate.common.config.CorsConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@AllArgsConstructor
@Slf4j
public class RecaptchaFilter extends OncePerRequestFilter {
    private final RecaptchaService recaptchaService;
    private final RecaptchaConfig recaptchaConfig;
    private final CorsConfig corsConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Check if recaptcha is enabled
        // Disabled is necessary to test endpoints without a frontend
        if (!recaptchaConfig.enabled()) {
            filterChain.doFilter(request, response);
            return;
        }

        if (request.getMethod().equals("OPTIONS")) {
            response.setHeader("Access-Control-Allow-Origin", corsConfig.getFrontendUrl());
            response.setHeader("Access-Control-Allow-Methods", "*");
            response.setHeader("Access-Control-Allow-Headers", "*");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setStatus(HttpStatus.OK.value());
            return;
        }

        log.info("Recaptcha filter was called");

        var urlForFilter = List.of("/api/v1/registrations", "/api/v1/login", "/api/v1/password-reset");

        if (urlForFilter.contains(request.getRequestURI())) {
            String recaptchaToken = request.getHeader("Recaptcha-Token");

            if (recaptchaToken == null || recaptchaToken.isBlank()) {
                var message = "The http request does not contain the reCaptcha token (null or empty)";
                log.warn(message);
                sendErrorResponse(response, request, "EMPTY_OR_NULL_RECAPTCHA", message);
                return;
            }

            RecaptchaResponse recaptchaResponse = recaptchaService.validateToken(recaptchaToken);
            log.info("{}", recaptchaResponse);
            if (!recaptchaResponse.success() || recaptchaResponse.score() < recaptchaConfig.scoreThreshold()) {
                var message = "Invalid reCaptcha token";
                log.warn(message);
                sendErrorResponse(response, request, "INVALID_RECAPTCHA", message);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private void sendErrorResponse(HttpServletResponse response, HttpServletRequest request, String erroTitle, String errorDetails)
            throws IOException {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ProblemDetail problem = ProblemDetail.forStatus(status);
        problem.setTitle(erroTitle);
        problem.setDetail(errorDetails);
        problem.setInstance(URI.create(request.getRequestURI()));

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(problem);

        response.setHeader("Access-Control-Allow-Origin", corsConfig.getFrontendUrl());
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(json);
    }
}
