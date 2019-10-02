package com.collab.domain.models;

import java.util.Objects;

public class CurrentUser {
    private final String name;

    private final String id;

    public CurrentUser(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrentUser that = (CurrentUser) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }

    @Override
    public String toString() {
        return "CurrentUser{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
