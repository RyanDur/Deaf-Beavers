package com.collab.translation;

import com.collab.domain.models.CurrentUser;
import com.collab.domain.models.NewUser;
import org.springframework.stereotype.Repository;

import static com.collab.translation.Converter.toDomain;
import static com.collab.translation.Converter.toEntity;

@Repository
public class UserRepository {

    private final JPAUserRepository repository;

    public UserRepository(JPAUserRepository repository) {
        this.repository = repository;
    }

    public CurrentUser save(NewUser newUser) {
        return toDomain(repository.save(toEntity(newUser)));
    }
}
