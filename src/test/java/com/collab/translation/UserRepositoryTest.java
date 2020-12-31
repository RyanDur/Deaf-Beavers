package com.collab.translation;

import com.collab.domain.models.OtherUser;
import com.collab.domain.models.CurrentUser;
import com.collab.domain.models.NewUser;
import com.collab.translation.models.Validation;
import com.collab.translation.models.Validations;
import io.vavr.control.Either;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@Testcontainers
@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@ContextConfiguration(initializers = {UserRepositoryTest.Initializer.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Autowired
    private Flyway flyway;

    @Container
    private static final MySQLContainer container = new MySQLContainer()
            .withDatabaseName("user_db");

    private String name;
    private NewUser newUser;
    private Either<Validations, CurrentUser> savedUser;

    @BeforeEach
    void setUp() {
        flyway.clean();
        flyway.migrate();
        name = "Face";
        newUser = new NewUser(name, "pass0");
        savedUser = repository.save(newUser);
    }

    @Test
    void shouldSaveAUser() {
        assertThat(savedUser.get().getName()).isEqualTo(name);
        assertThat(savedUser.get().getId()).isNotEmpty();
    }

    @Test
    void shouldNotSaveANameThatAlreadyExists() {
        Either<Validations, CurrentUser> actual = repository.save(newUser);
        Validations expected = Validations.of(Validation.of(newUser.getName(), "USERNAME_EXISTS"));
        assertThat(actual.left().get()).isEqualTo(expected);
    }

    @Test
    void shouldGetAllTheUsersExceptTheOneSpecified() {
        final NewUser tony = new NewUser("Tony", "pass1");
        final NewUser simon = new NewUser("Simon", "pass2");
        repository.save(tony);
        repository.save(simon);

        Page<OtherUser> actual = repository.getAll(savedUser.right().get().getId(), PageRequest.of(0, 3));
        List<String> usernames = actual.getContent().stream()
                .map(OtherUser::getName).collect(toList());

        assertThat(actual.getTotalElements()).isEqualTo(2);
        assertThat(usernames).contains(tony.getName());
        assertThat(usernames).contains(simon.getName());
    }

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + container.getJdbcUrl(),
                    "spring.datasource.username=" + container.getUsername(),
                    "spring.datasource.password=" + container.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
