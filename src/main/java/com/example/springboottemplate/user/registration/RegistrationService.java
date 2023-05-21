package com.example.springboottemplate.user.registration;

import com.example.springboottemplate.common.exception.Resource;
import com.example.springboottemplate.common.exception.ViolationException;
import com.example.springboottemplate.common.exception.ViolationType;
import com.example.springboottemplate.user.api.UserResponse;
import com.example.springboottemplate.user.common.User;
import com.example.springboottemplate.user.common.UserRepository;
import com.example.springboottemplate.user.service.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class RegistrationService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Transactional
    public UserResponse execute(RegistrationCommand command) {
        if (userRepository.existsByEmail(command.email())) {
            throw new ViolationException(Resource.USER, ViolationType.ALREADY_EXISTS, command.email());
        }

        User user = userRepository.save(
                new User(command.name(), command.email(), passwordEncoder.encode(command.password())));

        return userMapper.to(user);
    }
}
