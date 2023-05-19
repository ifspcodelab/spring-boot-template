package com.example.springboottemplate.user.service;

import com.example.springboottemplate.common.exception.Resource;
import com.example.springboottemplate.common.exception.ResourceNotFoundException;
import com.example.springboottemplate.user.api.UserResponse;
import com.example.springboottemplate.user.data.UserRepository;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetUserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    public UserResponse execute(UUID userId) {
        return mapper.to(
                repository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(userId, Resource.USER)));
    }
}
