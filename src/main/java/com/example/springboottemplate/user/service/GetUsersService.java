package com.example.springboottemplate.user.service;

import com.example.springboottemplate.user.api.UserResponse;
import com.example.springboottemplate.user.data.UserRepository;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetUsersService {
    private final UserRepository repository;
    private final UserMapper mapper;

    public List<UserResponse> execute() {
        return mapper.to(repository.findAll());
    }
}
