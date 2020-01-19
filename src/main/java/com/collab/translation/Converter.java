package com.collab.translation;

import com.collab.domain.models.OtherUser;
import com.collab.domain.models.CurrentUser;
import com.collab.domain.models.NewUser;
import com.collab.domain.models.UserStatus;
import com.collab.translation.models.CurrentUserResource;
import com.collab.translation.models.NewUserInput;
import com.collab.translation.models.OtherUserResource;
import com.collab.translation.models.UserEntity;

import java.util.UUID;

class Converter {
    static NewUser toCurrentUser(NewUserInput newUser) {
        return new NewUser(newUser.getName());
    }

    static CurrentUser toCurrentUser(UserEntity entity) {
        return new CurrentUser(entity.getId(), entity.getName(), UserStatus.valueOf(entity.getStatus()));
    }

    static UserEntity toEntity(NewUser newUser) {
        return new UserEntity(UUID.randomUUID().toString(), newUser.getName(), UserStatus.AVAILABLE.name());
    }

    static CurrentUserResource toResource(CurrentUser currentUser) {
        return new CurrentUserResource(currentUser.getId(), currentUser.getName(), currentUser.getStatus());
    }

    static OtherUser toOtherUser(UserEntity entity) {
        return new OtherUser(entity.getId(), entity.getName(), UserStatus.valueOf(entity.getStatus()));
    }

    public static OtherUserResource toOtherUserResource(OtherUser otherUser) {
        return new OtherUserResource(otherUser.getId(), otherUser.getName(), otherUser.getStatus());
    }
}
