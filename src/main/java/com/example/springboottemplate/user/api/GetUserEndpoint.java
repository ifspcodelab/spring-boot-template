package com.example.springboottemplate.user.api;

import com.example.springboottemplate.user.service.GetUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.AllArgsConstructor;

import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/users")
@AllArgsConstructor
@Tag(name = "Users", description = "User management APIs")
public class GetUserEndpoint {
    private final GetUserService getUserService;

    @Operation(
            summary = "Get a user by id",
            description = "Get a user by id",
            tags = {"Users"})
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Success",
                content =
                        @Content(
                                schema = @Schema(implementation = UserResponse.class),
                                mediaType = "application/json")),
        @ApiResponse(
                responseCode = "404",
                description = "Not Found",
                content =
                        @Content(
                                schema = @Schema(implementation = ProblemDetail.class),
                                mediaType = "application/json"))
    })
    @GetMapping("{userId}")
    public ResponseEntity<UserResponse> get(@PathVariable UUID userId) {
        return ResponseEntity.ok(getUserService.execute(userId));
    }
}
