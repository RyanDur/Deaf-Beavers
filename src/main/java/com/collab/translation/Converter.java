package com.collab.translation;

import com.collab.domain.models.CurrentUser;
import com.collab.domain.models.NewUser;
import com.collab.translation.models.CurrentUserResource;
import com.collab.translation.models.NewUserInput;
import com.collab.translation.models.UserEntity;

import java.util.UUID;

class Converter {
    static NewUser toDomain(NewUserInput newUser) {
        return new NewUser(newUser.getName());
    }

    static CurrentUser toDomain(UserEntity entity) {
        return new CurrentUser(entity.getId(), entity.getName());
    }

    static UserEntity toEntity(NewUser newUser) {
        return new UserEntity(UUID.randomUUID().toString(), newUser.getName());
    }

    static CurrentUserResource toResource(CurrentUser currentUser) {
        return new CurrentUserResource(currentUser.getId(), currentUser.getName());
    }
}
