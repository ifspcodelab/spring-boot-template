package com.example.springboottemplate.user.api;

import com.example.springboottemplate.user.service.CreateUserCommand;
import com.example.springboottemplate.user.service.CreateUserService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("api/v1/users")
@AllArgsConstructor
@Tag(name = "Users", description = "User management APIs")
public class CreateUserEndpoint {
    private final CreateUserService createUserService;

    @Operation(
            summary = "Create a new user",
            description = "Create a new user",
            tags = {"Users"})
    @PostMapping
    public ResponseEntity<UserResponse> post(@RequestBody @Valid CreateUserCommand command) {
        return new ResponseEntity<>(createUserService.execute(command), HttpStatus.CREATED);
    }
}
