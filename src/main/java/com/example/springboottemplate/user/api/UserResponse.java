package com.example.springboottemplate.user.api;

import java.util.UUID;

public record UserResponse(UUID id, String name, String email) {}
