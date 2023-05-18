package com.example.springboottemplate.user.service;

import com.example.springboottemplate.common.exception.Resource;
import com.example.springboottemplate.common.exception.ViolationException;
import com.example.springboottemplate.common.exception.ViolationType;
import com.example.springboottemplate.user.api.UserResponse;
import com.example.springboottemplate.user.data.User;
import com.example.springboottemplate.user.data.UserRepository;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateUserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    public UserResponse execute(CreateUserCommand command) {
        if (repository.existsByEmail(command.email())) {
            throw new ViolationException(
                    Resource.USER, ViolationType.ALREADY_EXISTS, "Email already exists");
        }

        User user = repository.save(new User(command.name(), command.email()));

        return mapper.to(user);
    }
}
