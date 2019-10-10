package com.collab.translation;

import com.collab.domain.UserService;
import com.collab.translation.models.CurrentUserResource;
import com.collab.translation.models.NewUserInput;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.collab.translation.Converter.toDomain;
import static com.collab.translation.Converter.toResource;

@RestController
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping(
            name = "save",
            path = "/users",
            consumes = "application/json",
            produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public CurrentUserResource save(@Valid @RequestBody NewUserInput newUser) {
        return toResource(service.save(toDomain(newUser)));
    }
}
