package com.collab.domain.models;

import com.collab.translation.models.UserEntity;

import java.util.UUID;

import static com.collab.domain.models.Status.AVAILABLE;

public class NewUser {
    private final String name;
    private final String password;

    public NewUser(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public UserEntity toEntity() {
        return new UserEntity(UUID.randomUUID().toString(), getName(), AVAILABLE, getPassword());
    }
}
