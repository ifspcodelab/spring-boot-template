CREATE TABLE password_resets(
    id UUID NOT NULL,
    user_id UUID NOT NULL,
    token UUID NOT NULL,
    used BOOLEAN NOT NULL,
    created_at TIMESTAMP NOT NULL,
    expires_on TIMESTAMP NOT NULL,
    CONSTRAINT password_resets_pk PRIMARY KEY (id),
    CONSTRAINT users_fk FOREIGN KEY (user_id) REFERENCES users(id)
);
