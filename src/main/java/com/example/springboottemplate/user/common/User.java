package com.example.springboottemplate.user.common;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    private UUID id;

    private String name;
    private String email;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private List<Role> roles;

    private Boolean enabled;
    private Boolean locked;
    private Instant createdAt;
    private Instant updatedAt;

    public User(String name, String email, String password) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = List.of(Role.SIMPLE_USER, Role.ADMIN);
        this.enabled = false;
        this.locked = false;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }
}
