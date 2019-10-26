package com.collab.translation;

import com.collab.domain.UserService;
import com.collab.translation.models.NewUserInput;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.collab.translation.Converter.toCurrentUser;
import static com.collab.translation.Converter.toResource;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Patterns.$Left;
import static io.vavr.Patterns.$Right;
import static java.net.URI.create;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.created;

@RestController
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping(path = "/users", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> save(@Valid @RequestBody NewUserInput newUser) {
        return Match(service.save(toCurrentUser(newUser))).of(
                Case($Right($()), currentUser -> created(create("/users/" + currentUser.getId()))
                        .body(toResource(currentUser))),
                Case($Left($()), validation -> badRequest()
                        .body(validation)));
    }

    @GetMapping(path = "/users", produces = "application/json")
    public ResponseEntity<?> getAll(@RequestParam String exclude, Pageable pageable) {
        return ResponseEntity.ok(service.getAll(exclude, pageable)
                .map(Converter::toOtherUserResource));
    }
}
