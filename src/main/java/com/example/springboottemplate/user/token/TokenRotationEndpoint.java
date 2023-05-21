package com.example.springboottemplate.user.token;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/refresh-tokens/rotation")
@AllArgsConstructor
public class TokenRotationEndpoint {
    private final TokenRotationService tokenRotationService;

    @PostMapping
    public ResponseEntity<TokenResponse> rotation(@RequestBody @Valid TokenRotationCommand command) {
        return new ResponseEntity<>(tokenRotationService.execute(command), HttpStatus.CREATED);
    }

    @ExceptionHandler(TokenRotationException.class)
    public ResponseEntity<ProblemDetail> handleTokenRotationException(TokenRotationException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
        problem.setTitle(ex.getReason().name());
        problem.setDetail(ex.getReason().getMessage());
        return new ResponseEntity<>(problem, HttpStatus.FORBIDDEN);
    }
}
