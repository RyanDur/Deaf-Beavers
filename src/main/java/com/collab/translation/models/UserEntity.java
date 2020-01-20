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
    private String status;

    public UserEntity() {
    }

    public UserEntity(String id, String name, String status) {
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

    public String getStatus() {
        return status;
    }
}
