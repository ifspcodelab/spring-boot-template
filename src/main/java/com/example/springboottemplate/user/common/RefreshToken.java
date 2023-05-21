package com.example.springboottemplate.user.common;

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
@Table(name = "refresh_tokens")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RefreshToken {
    @Id
    private UUID id;

    private Instant createdAt;
    private Instant expiresAt;
    private Boolean revoked;

    @ManyToOne
    private User user;

    public RefreshToken(User user, Long durationInSeconds) {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.expiresAt = Instant.now().plusSeconds(durationInSeconds);
        this.revoked = Boolean.FALSE;
        this.user = user;
    }
}
