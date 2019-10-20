package com.collab.translation;

import com.collab.domain.models.CurrentUser;
import com.collab.domain.models.NewUser;
import com.collab.translation.models.Validation;
import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@Testcontainers
@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@ContextConfiguration(initializers = {UserRepositoryTest.Initializer.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Container
    private static MySQLContainer container = new MySQLContainer()
            .withDatabaseName("user");

    private String name;
    private NewUser newUser;
    private Either<Validation, CurrentUser> savedUser;

    @BeforeEach
    void setUp() {
        name = "Face";
        newUser = new NewUser(name);
        savedUser = repository.save(newUser);
    }

    @Test
    void shouldSaveAUser() {
        assertThat(savedUser.get().getName()).isEqualTo(name);
        assertThat(savedUser.get().getId()).isNotEmpty();
    }

    @Test
    public void shouldNotSaveANameThatAlreadyExists() {
        Either<Validation, CurrentUser> actual = repository.save(newUser);
        Validation expected = Validation.of(newUser.getName(), "USERNAME_EXISTS");
        assertThat(actual.left().get()).isEqualTo(expected);
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
