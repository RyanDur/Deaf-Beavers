package com.collab.domain;

import com.collab.domain.models.CurrentUser;
import com.collab.domain.models.NewUser;
import com.collab.domain.models.Status;
import com.collab.translation.UserRepository;
import com.collab.translation.models.Validations;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static io.vavr.control.Either.right;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    @Test
    void shouldTakeTheNewUserAndSaveIt() {
        String name = "Ryan";
        NewUser newUser = new NewUser(name);
        CurrentUser expected = new CurrentUser("face", name, Status.AVAILABLE);
        when(repository.save(newUser)).thenReturn(right(expected));

        Either<Validations, CurrentUser> actual = service.save(newUser);

        assertThat(actual.get()).isEqualTo(expected);
    }
}
