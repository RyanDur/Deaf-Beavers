package com.collab.translation.models;

import com.collab.domain.models.UserStatus;

import java.util.Objects;

public class CurrentUserResource {

    private String id;

    private String name;
    private UserStatus status;

    public CurrentUserResource(String id, String name, UserStatus status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public UserStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrentUserResource that = (CurrentUserResource) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, status);
    }

    @Override
    public String toString() {
        return "CurrentUserResource{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}
