package com.example.springboottemplate.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ViolationType {
    ALREADY_EXISTS("already exists");

    private String name;
}
