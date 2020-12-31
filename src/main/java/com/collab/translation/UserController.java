package com.collab.translation;

import com.collab.domain.UserService;
import com.collab.domain.models.OtherUser;
import com.collab.translation.models.NewUserInput;
import com.collab.translation.models.UserStatusInput;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static io.vavr.API.*;
import static io.vavr.Patterns.$Left;
import static io.vavr.Patterns.$Right;
import static java.net.URI.create;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.created;

@RestController
public class UserController {

    private final UserService service;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService service, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(path = "/users", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> save(@Valid @RequestBody NewUserInput newUserInput) {
        return Match(service.save(newUserInput.toNewUser(passwordEncoder))).of(
                Case($Right($()), currentUser -> created(create("/users/" + currentUser.getId()))
                        .body(currentUser.toResource())),
                Case($Left($()), validation -> badRequest()
                        .body(validation)));
    }

    @GetMapping(path = "/users", produces = "application/json")
    public ResponseEntity<?> getAll(@RequestParam String exclude, Pageable pageable) {
        return ResponseEntity.ok(service.getAll(exclude, pageable)
                .map(OtherUser::toOtherUserResource));
    }

    @PatchMapping(path = "/users/{userId}", produces = "application/json")
    public ResponseEntity<?> update(@PathVariable String userId, @RequestBody UserStatusInput statusInput) {
        service.update(userId, statusInput.toUserStatus());
        return ResponseEntity.noContent().build();
    }
}
