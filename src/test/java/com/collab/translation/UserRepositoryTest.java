package com.collab.translation;

import com.collab.domain.models.CurrentUser;
import com.collab.domain.models.NewUser;
import com.collab.translation.models.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

    @Mock
    private JPAUserRepository jpaUserRepository;

    @InjectMocks
    private UserRepository repository;

    private String name;
    private String id;
    private NewUser newUser;

    @BeforeEach
    void setUp() {
        name = "Face";
        id = "your";
        newUser = new NewUser(name);
        UserEntity userEntity = new UserEntity(id, name);
        when(jpaUserRepository.save(any(UserEntity.class))).thenReturn(userEntity);
    }

    @Test
    void shouldSaveAUser() {
        CurrentUser expected = new CurrentUser(id, name);
        CurrentUser actual = repository.save(newUser);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldGenerateAnId() {
        ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);
        repository.save(newUser);
        verify(jpaUserRepository).save(captor.capture());

        assertThat(captor.getValue().getId()).isNotEmpty();
    }
}
