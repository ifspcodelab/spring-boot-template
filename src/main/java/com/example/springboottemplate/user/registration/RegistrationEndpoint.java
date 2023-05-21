package com.example.springboottemplate.user.registration;

import com.example.springboottemplate.user.api.UserResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/registrations")
@AllArgsConstructor
@Tag(name = "Registration", description = "Registration APIs")
public class RegistrationEndpoint {
    private final RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<UserResponse> post(@RequestBody @Valid RegistrationCommand command) {
        return new ResponseEntity<>(registrationService.execute(command), HttpStatus.CREATED);
    }
}
