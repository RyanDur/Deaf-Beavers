package com.collab.translation;

import com.collab.domain.models.CurrentUser;
import com.collab.domain.models.NewUser;
import com.collab.translation.models.UserEntity;
import com.collab.translation.models.Validation;
import com.collab.translation.models.Validations;
import io.vavr.control.Either;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import static com.collab.translation.Converter.toDomain;
import static com.collab.translation.Converter.toEntity;
import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;
import static java.util.Objects.isNull;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity getByName(String name);

    default Either<Validations, CurrentUser> save(NewUser newUser) {
        return isNull(getByName(newUser.getName())) ?
                right(toDomain(save(toEntity(newUser)))) :
                left(Validations.of(Validation.of(newUser.getName(), "USERNAME_EXISTS")));
    }
}
