package com.example.springboottemplate.user.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LoginCommand(@Email String email, @NotNull @NotEmpty @Size(min = 6, max = 20) String password) {}
