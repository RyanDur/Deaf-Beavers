package com.collab.domain;

import com.collab.domain.models.CurrentUser;
import com.collab.domain.models.NewUser;
import com.collab.translation.UserRepository;
import com.collab.translation.models.Validation;
import io.vavr.control.Either;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Either<Validation, CurrentUser> save(NewUser newUser) {
        return repository.save(newUser);
    }
}
