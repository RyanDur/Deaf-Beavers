package com.collab.domain;

import com.collab.domain.models.CurrentUser;
import com.collab.domain.models.NewUser;
import com.collab.translation.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public CurrentUser save(NewUser newUser) {
        return repository.save(newUser);
    }
}
