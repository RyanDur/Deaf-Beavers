package com.collab.domain.models;

public enum UserStatus {
    AVAILABLE("AVAILABLE"), LOGGED_OUT("LOGGED_OUT");

    private String status;

    UserStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
