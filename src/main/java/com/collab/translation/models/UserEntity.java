package com.collab.translation.models;

import com.collab.domain.models.CurrentUser;
import com.collab.domain.models.OtherUser;
import com.collab.domain.models.Status;

import javax.persistence.*;

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

    private String password;

    private String status;

    public UserEntity() {
    }

    public UserEntity(String id, String name, Status status, String password) {
        this.id = id;
        this.name = name;
        this.status = status.name();
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public CurrentUser toCurrentUser() {
        return new CurrentUser(getId(), getName(), Status.valueOf(getStatus()));
    }

    public OtherUser toOtherUser() {
        return new OtherUser(getId(), getName(), Status.valueOf(getStatus()));
    }
}
