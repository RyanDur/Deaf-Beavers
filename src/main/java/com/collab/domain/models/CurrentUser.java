package com.collab.domain.models;

import java.util.Objects;

public class CurrentUser {
    private final String name;
    private Status status;
    private final String id;

    public CurrentUser(String id, String name, Status status) {
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

    public Status getStatus() {
        return status;
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
