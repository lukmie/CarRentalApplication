package com.sda.carrentapp.entity;

public class SignUpRequest {
    private String user;
    private String password;
    private String role;

    public SignUpRequest() {
    }

    public SignUpRequest(String user, String password, String role) {
        this.user = user;
        this.password = password;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
