package com.example.springboottemplate.user.api;

import java.time.Instant;
import java.util.UUID;

public record UserResponse(
        UUID id, String name, String email, Boolean enabled, Boolean locked, Instant createdAt, Instant updatedAt) {}
