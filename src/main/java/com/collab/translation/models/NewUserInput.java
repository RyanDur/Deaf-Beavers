package com.collab.translation.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.Objects;

@JsonDeserialize(builder = NewUserInput.Builder.class)
public class NewUserInput {

    @MaxSizeUserName
    private String name;

    private NewUserInput(Builder builder) {
        name = builder.name;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewUserInput that = (NewUserInput) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {
        private String name;

        public Builder name(String val) {
            name = val;
            return this;
        }

        public NewUserInput build() {
            return new NewUserInput(this);
        }
    }
}

