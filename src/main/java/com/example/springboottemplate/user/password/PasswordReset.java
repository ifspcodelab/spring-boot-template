package com.example.springboottemplate.user.password;

import com.example.springboottemplate.user.common.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "password_resets")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PasswordReset {
    @Id
    private UUID id;

    private UUID token;
    private Instant createdAt;
    private Instant expiresOn;
    private Boolean used;

    @ManyToOne
    private User user;

    public PasswordReset(User user, Long durationInSeconds) {
        this.id = UUID.randomUUID();
        this.token = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.expiresOn = this.createdAt.plusSeconds(durationInSeconds);
        this.used = Boolean.FALSE;
        this.user = user;
    }
}
