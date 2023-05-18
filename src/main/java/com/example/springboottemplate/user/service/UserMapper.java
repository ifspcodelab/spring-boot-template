package com.example.springboottemplate.user.service;

import com.example.springboottemplate.user.api.UserResponse;
import com.example.springboottemplate.user.data.User;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse to(User user);

    List<UserResponse> to(List<User> users);
}
