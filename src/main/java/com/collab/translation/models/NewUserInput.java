package com.collab.translation.models;

import com.collab.domain.models.NewUser;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

@JsonDeserialize(builder = NewUserInput.Builder.class)
public class NewUserInput {

    @MaxSizeUserName
    private final String name;
    private final String password;

    private NewUserInput(Builder builder) {
        name = builder.name;
        password = builder.password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public NewUser toNewUser(PasswordEncoder passwordEncoder) {
        return new NewUser(getName(), passwordEncoder.encode(getPassword()));
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewUserInput that = (NewUserInput) o;
        return Objects.equals(name, that.name) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, password);
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {
        private String name;
        private String password;

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder password(String val) {
            password = val;
            return this;
        }

        public NewUserInput build() {
            return new NewUserInput(this);
        }
    }
}

