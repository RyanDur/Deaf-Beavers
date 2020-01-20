package com.collab.domain.models;

import java.util.Objects;

public class OtherUser {

    private final String id;
    private final String name;
    private Status status;

    public OtherUser(String id, String name, Status status) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OtherUser otherUser = (OtherUser) o;
        return Objects.equals(id, otherUser.id) &&
                Objects.equals(name, otherUser.name) &&
                status == otherUser.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, status);
    }

    @Override
    public String toString() {
        return "OtherUser{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}
