package com.collab.translation;

import com.collab.domain.models.OtherUser;
import com.collab.domain.models.CurrentUser;
import com.collab.domain.models.NewUser;
import com.collab.translation.models.UserEntity;
import com.collab.translation.models.Validation;
import com.collab.translation.models.Validations;
import io.vavr.control.Either;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import static com.collab.translation.Converter.toCurrentUser;
import static com.collab.translation.Converter.toEntity;
import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;
import static java.util.Objects.isNull;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {

    UserEntity getByName(String name);

    @Query("SELECT u FROM UserEntity u WHERE u.id <> :id")
    Page<UserEntity> findAllExcept(@Param("id") String exclude, Pageable pageable);

    default Either<Validations, CurrentUser> save(NewUser newUser) {
        return isNull(getByName(newUser.getName())) ?
                right(toCurrentUser(save(toEntity(newUser)))) :
                left(Validations.builder()
                        .username(Validation.of(newUser.getName(), "USERNAME_EXISTS"))
                        .build());
    }

    default Page<OtherUser> getAll(String exclude, Pageable pageable) {
        return findAllExcept(exclude, pageable)
                .map(Converter::toOtherUser);
    }
}
