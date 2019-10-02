package com.collab.domain;

import com.collab.domain.models.CurrentUser;
import com.collab.domain.models.NewUser;
import com.collab.translation.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

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
        CurrentUser expected = new CurrentUser("face", name);
        Mockito.when(repository.save(newUser)).thenReturn(expected);

        CurrentUser actual = service.save(newUser);

        assertThat(actual).isEqualTo(expected);
    }
}
