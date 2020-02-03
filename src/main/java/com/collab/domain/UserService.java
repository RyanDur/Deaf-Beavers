package com.collab.domain;

import com.collab.domain.models.CurrentUser;
import com.collab.domain.models.NewUser;
import com.collab.domain.models.OtherUser;
import com.collab.domain.models.UserStatus;
import com.collab.translation.UserRepository;
import com.collab.translation.models.Validations;
import io.vavr.control.Either;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Either<Validations, CurrentUser> save(NewUser newUser) {
        return repository.save(newUser);
    }

    public Page<OtherUser> getAll(String exclude, Pageable pageable) {
        return repository.getAll(exclude, pageable);
    }

    public void update(String userId, UserStatus userStatus) {
        repository.updateUsersStatus(userId, userStatus.getStatus().name());
    }
}
