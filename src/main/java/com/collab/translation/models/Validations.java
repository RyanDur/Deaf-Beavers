package com.collab.translation.models;

import java.util.Objects;

public class Validations {

    private final Validation username;

    private Validations(Validation validation) {
        this.username = validation;
    }

    public static Validations of(Validation validation) {
        return new Validations(validation);
    }

    public Validation getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Validations that = (Validations) o;
        return Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public String toString() {
        return "Validations{" +
                "username=" + username +
                '}';
    }
}
