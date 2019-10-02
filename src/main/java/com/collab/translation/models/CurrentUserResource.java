package com.collab.translation.models;

import java.util.Objects;

public class CurrentUserResource {

    private String id;

    private String name;

    public CurrentUserResource(String id, String name) {
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
        CurrentUserResource that = (CurrentUserResource) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "CurrentUserResource{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
