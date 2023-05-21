CREATE TABLE users(
    id UUID NOT NULL,
    name VARCHAR NOT NULL,
    email VARCHAR NOT NULL UNIQUE,
    password CHAR(60) NOT NULL,
    enabled BOOLEAN NOT NULL,
    locked BOOLEAN NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT users_pk PRIMARY KEY (id)
);
