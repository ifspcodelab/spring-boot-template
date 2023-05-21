package com.example.springboottemplate.common.security;

import com.example.springboottemplate.user.common.User;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@AllArgsConstructor
@Getter
public class JwtUserDetails implements UserDetails {
    private final UUID id;
    private final String username;
    private Boolean enabled;
    private Boolean locked;
    private final List<SimpleGrantedAuthority> grantedAuthorities;

    public static JwtUserDetails fromUser(User user) {
        return new JwtUserDetails(
                user.getId(),
                user.getEmail(),
                user.getEnabled(),
                user.getLocked(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                        .collect(Collectors.toList()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
