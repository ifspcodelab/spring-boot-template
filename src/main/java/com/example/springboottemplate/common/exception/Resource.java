package com.example.springboottemplate.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Resource {
    USER("User");

    private final String name;
}
