package com.example.springboottemplate.user.service;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserCommand(@NotNull @NotBlank String name, @NotNull @Email String email) {}
