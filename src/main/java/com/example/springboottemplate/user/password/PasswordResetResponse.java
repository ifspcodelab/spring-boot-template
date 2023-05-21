package com.example.springboottemplate.user.password;

import java.time.Instant;
import java.util.UUID;

public record PasswordResetResponse(UUID id, Instant createdAt, Instant expiresOn, Boolean used) {}
