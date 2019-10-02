package com.collab.translation;

import com.collab.translation.models.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface JPAUserRepository extends CrudRepository<UserEntity, Long> {
}
