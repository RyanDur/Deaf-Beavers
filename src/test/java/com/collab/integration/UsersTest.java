package com.collab.integration;

import com.collab.translation.models.CurrentUserResource;
import com.collab.translation.models.NewUserInput;
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

import static com.jayway.jsonpath.JsonPath.read;
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

    @Container
    private static MySQLContainer container = new MySQLContainer().withDatabaseName("user");

    @Test
    void shouldCreateAUser() {
        String userName = "Ryan";
        NewUserInput user = NewUserInput.builder().name(userName).build();
        CurrentUserResource expected = new CurrentUserResource("some id", userName);

        ResponseEntity<String> actual = restTemplate.postForEntity(
                endpoint("/users"), user, String.class);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        String name = read(actual.getBody(), "$.name");
        assertThat(name).isEqualTo(expected.getName());
    }

    private String endpoint(String path) {
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
