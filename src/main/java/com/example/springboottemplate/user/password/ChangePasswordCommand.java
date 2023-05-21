package com.example.springboottemplate.user.password;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ChangePasswordCommand(@NotNull @NotEmpty @Size(min = 6, max = 20) String password) {}
