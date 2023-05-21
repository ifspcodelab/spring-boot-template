package com.example.springboottemplate.user.password;

import jakarta.validation.Valid;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/password-reset")
@AllArgsConstructor
public class PasswordResetEndpoint {
    private final PasswordResetService passwordResetService;

    @PostMapping
    public ResponseEntity<PasswordResetResponse> requestReset(@RequestBody @Valid PasswordResetCommand command) {
        return new ResponseEntity<>(passwordResetService.requestReset(command), HttpStatus.CREATED);
    }

    @PostMapping("{token}")
    public ResponseEntity<PasswordResetResponse> changePassword(
            @PathVariable UUID token, @RequestBody @Valid ChangePasswordCommand command) {
        return new ResponseEntity<>(passwordResetService.changePassword(token, command), HttpStatus.CREATED);
    }

    @ExceptionHandler(PasswordResetException.class)
    public ResponseEntity<ProblemDetail> handleLoginException(PasswordResetException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problem.setTitle(ex.getReason().name());
        problem.setDetail(ex.getReason().getMessage());
        return new ResponseEntity<>(problem, HttpStatus.CONFLICT);
    }
}
