package com.example.springboottemplate.user.password;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record PasswordResetCommand(@NotNull @Email String email) {}
