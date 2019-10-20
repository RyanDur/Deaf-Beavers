package com.collab.translation.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
        name = "user",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name", name = "USERNAME_EXISTS")
        }
)
public class UserEntity {

    @Id
    private String id;

    @Column(unique = true)
    private String name;

    public UserEntity() {
    }

    public UserEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
