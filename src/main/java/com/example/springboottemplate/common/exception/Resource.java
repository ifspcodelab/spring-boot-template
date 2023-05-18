package com.example.springboottemplate.common.exception;

import lombok.Getter;

@Getter
public enum Resource {
    USER("User");

    private String name;

    Resource(String name) {
        this.name = name;
    }
}
