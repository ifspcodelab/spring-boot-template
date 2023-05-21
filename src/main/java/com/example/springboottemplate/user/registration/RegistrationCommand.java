package com.example.springboottemplate.user.registration;

import jakarta.validation.constraints.*;

public record RegistrationCommand(
        @NotNull @NotEmpty String name,
        @Email String email,
        @NotNull @NotEmpty @Size(min = 6, max = 20) String password) {}
