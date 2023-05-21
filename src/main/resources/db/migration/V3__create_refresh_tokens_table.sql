CREATE TABLE refresh_tokens(
      id UUID NOT NULL,
      user_id UUID NOT NULL,
      created_at TIMESTAMP NOT NULL,
      expires_at TIMESTAMP NOT NULL,
      revoked BOOLEAN NOT NULL,
      CONSTRAINT refresh_tokens_pk PRIMARY KEY (id),
      CONSTRAINT users_fk FOREIGN KEY (user_id) REFERENCES users(id)
);
