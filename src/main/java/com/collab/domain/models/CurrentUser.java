package com.collab.domain.models;

import com.collab.translation.models.CurrentUserResource;

import java.util.Objects;

public class CurrentUser {
    private final String id;
    private final String name;
    private final Status status;

    public CurrentUser(String id, String name, Status status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Status getStatus() {
        return status;
    }

    public CurrentUserResource toResource() {
        return new CurrentUserResource(getId(), getName(), getStatus());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrentUser that = (CurrentUser) o;
        return Objects.equals(name, that.name) &&
                status == that.status &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, status, id);
    }

    @Override
    public String toString() {
        return "CurrentUser{" +
                "name='" + name + '\'' +
                ", status=" + status +
                ", id='" + id + '\'' +
                '}';
    }
}
