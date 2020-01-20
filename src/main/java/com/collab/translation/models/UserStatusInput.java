package com.collab.translation.models;

import com.collab.domain.models.Status;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.Objects;

@JsonDeserialize(builder = UserStatusInput.Builder.class)
public class UserStatusInput {

    private Status status;

    private UserStatusInput(Builder builder) {
        status = builder.status;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserStatusInput that = (UserStatusInput) o;
        return status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(status);
    }

    @Override
    public String toString() {
        return "UserStatus{" +
                "status=" + status +
                '}';
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {
        private Status status;

        public Builder() {
        }

        public Builder status(Status val) {
            status = val;
            return this;
        }

        public UserStatusInput build() {
            return new UserStatusInput(this);
        }
    }
}
