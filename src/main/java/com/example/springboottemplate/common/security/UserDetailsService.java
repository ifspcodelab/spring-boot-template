package com.example.springboottemplate.common.security;

import com.example.springboottemplate.user.common.User;
import com.example.springboottemplate.user.common.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =
                userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return JwtUserDetails.fromUser(user);
    }
}
