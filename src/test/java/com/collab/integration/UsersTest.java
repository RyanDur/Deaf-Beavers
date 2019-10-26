package com.collab.integration;

import com.collab.translation.models.CurrentUserResource;
import com.collab.translation.models.NewUserInput;
import io.vavr.Tuple2;
import org.flywaydb.core.Flyway;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.jayway.jsonpath.JsonPath.read;
import static io.vavr.Tuple.of;
import static java.lang.String.join;
import static java.util.Arrays.stream;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {UsersTest.Initializer.class})
class UsersTest {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private Flyway flyway;

    @Container
    private static MySQLContainer container = new MySQLContainer()
            .withDatabaseName("user");

    private String userName = "Ryan";
    private ResponseEntity<String> currentUserResponse;
    private String currentUser;

    @BeforeEach
    void setUp() {
        flyway.clean();
        flyway.migrate();
        currentUserResponse = createUsers(userName).get(0);
        currentUser = read(currentUserResponse.getBody(), "$.id");
    }

    @Test
    void shouldCreateAUser() {
        CurrentUserResource expected = new CurrentUserResource("some id", userName);

        assertThat(currentUserResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        String name = read(currentUserResponse.getBody(), "$.name");
        assertThat(currentUserResponse.getHeaders().get("Location")).isEqualTo(singletonList("/users/" + currentUser));
        assertThat(name).isEqualTo(expected.getName());
    }

    @Test
    void shouldGetAllTheOtherUsers() {
        createUsers("Paulina", "Nicole", "Chris");

        int size = 2;
        int number = 0;

        ResponseEntity<String> response = restTemplate.getForEntity(
                uri("/users" + params(of("exclude", currentUser), of("page", number), of("size", size))),
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Integer actualSize = read(response.getBody(), "$.size");
        assertThat(actualSize).isEqualTo(size);

        Integer actualTotalElements = read(response.getBody(), "$.totalElements");
        assertThat(actualTotalElements).isEqualTo(3);

        Integer actualTotalPages = read(response.getBody(), "$.totalPages");
        assertThat(actualTotalPages).isEqualTo(2);

        Integer actualNumber = read(response.getBody(), "$.number");
        assertThat(actualNumber).isEqualTo(number);
    }

    @NotNull
    private String params(Tuple2<?, ?>... elements) {
        return "?" + Stream.of(elements).map(elem ->
                join("=", "" + elem._1(), "" + elem._2()))
                .collect(Collectors.joining("&"));
    }

    private List<ResponseEntity<String>> createUsers(String... users) {
        return stream(users).map(user -> restTemplate.postForEntity(uri("/users"),
                NewUserInput.builder().name(user).build(), String.class)).collect(toList());
    }

    private String uri(String path) {
        return "http://localhost:" + randomServerPort + path;
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
