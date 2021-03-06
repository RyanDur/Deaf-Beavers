package com.collab.translation;

import com.collab.domain.models.CurrentUser;
import com.collab.domain.models.NewUser;
import com.collab.domain.models.OtherUser;
import com.collab.translation.models.UserEntity;
import com.collab.translation.models.Validation;
import com.collab.translation.models.Validations;
import io.vavr.control.Either;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
                right(save(newUser.toEntity()).toCurrentUser()) :
                left(Validations.builder()
                        .username(Validation.of(newUser.getName(), "USERNAME_EXISTS"))
                        .build());
    }

    default Page<OtherUser> getAll(String exclude, Pageable pageable) {
        return findAllExcept(exclude, pageable)
                .map(UserEntity::toOtherUser);
    }

    @Transactional
    @Modifying
    @Query("UPDATE UserEntity u SET u.status = :status WHERE u.id = :id")
    void updateUsersStatus(@Param("id") String userId, @Param("status") String status);
}
