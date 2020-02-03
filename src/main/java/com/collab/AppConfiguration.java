package com.collab;

import com.collab.domain.UserService;
import com.collab.translation.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    public UserService userService(UserRepository repository) {
        return new UserService(repository);
    }
}
