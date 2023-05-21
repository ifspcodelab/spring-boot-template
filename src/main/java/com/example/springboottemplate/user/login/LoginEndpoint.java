package com.example.springboottemplate.user.login;

import com.example.springboottemplate.user.token.TokenResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/login")
@AllArgsConstructor
@Tag(name = "Login", description = "Login APIs")
public class LoginEndpoint {
    private final LoginService loginService;

    @PostMapping
    public ResponseEntity<TokenResponse> post(@RequestBody @Valid LoginCommand command) {
        return new ResponseEntity<>(loginService.execute(command), HttpStatus.CREATED);
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ProblemDetail> handleLoginException(LoginException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
        problem.setTitle(ex.getReason().name());
        problem.setDetail(ex.getReason().getMessage());
        return new ResponseEntity<>(problem, HttpStatus.FORBIDDEN);
    }
}
