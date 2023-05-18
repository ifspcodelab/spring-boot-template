package com.example.springboottemplate.user.api;

import com.example.springboottemplate.user.service.GetUsersService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@AllArgsConstructor
@Tag(name = "Users", description = "User management APIs")
public class GetUsersEndpoint {
    private final GetUsersService getUsersService;

    @Operation(
            summary = "Get all users",
            description = "Get a list of users.",
            tags = {"Users"})
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                content = {
                    @Content(
                            array =
                                    @ArraySchema(
                                            schema = @Schema(implementation = UserResponse.class)),
                            mediaType = "application/json")
                })
    })
    @GetMapping
    public ResponseEntity<List<UserResponse>> get() {
        return ResponseEntity.ok(getUsersService.execute());
    }
}
