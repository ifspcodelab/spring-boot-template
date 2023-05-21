package com.example.springboottemplate.user.service;

import com.example.springboottemplate.user.api.UserResponse;
import com.example.springboottemplate.user.common.User;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse to(User user);

    List<UserResponse> to(List<User> users);
}
