package com.collab.domain;

import java.util.Objects;

public class OtherUser {

    private final String id;
    private final String name;

    public OtherUser(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OtherUser otherUser = (OtherUser) o;
        return Objects.equals(id, otherUser.id) &&
                Objects.equals(name, otherUser.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "OtherUser{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
