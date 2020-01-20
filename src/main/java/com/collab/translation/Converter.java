package com.collab.translation;

import com.collab.domain.models.*;
import com.collab.translation.models.*;

import java.util.UUID;

class Converter {
    static NewUser toCurrentUser(NewUserInput newUser) {
        return new NewUser(newUser.getName());
    }

    static CurrentUser toCurrentUser(UserEntity entity) {
        return new CurrentUser(entity.getId(), entity.getName(), Status.valueOf(entity.getStatus()));
    }

    static UserEntity toEntity(NewUser newUser) {
        return new UserEntity(UUID.randomUUID().toString(), newUser.getName(), Status.AVAILABLE.name());
    }

    static CurrentUserResource toResource(CurrentUser currentUser) {
        return new CurrentUserResource(currentUser.getId(), currentUser.getName(), currentUser.getStatus());
    }

    static OtherUser toOtherUser(UserEntity entity) {
        return new OtherUser(entity.getId(), entity.getName(), Status.valueOf(entity.getStatus()));
    }

    public static OtherUserResource toOtherUserResource(OtherUser otherUser) {
        return new OtherUserResource(otherUser.getId(), otherUser.getName(), otherUser.getStatus());
    }

    public static UserStatus toUserStatus(UserStatusInput statusInput) {
        return new UserStatus(statusInput.getStatus());
    }
}
