package com.itacademy.virtualpet.model;

public class LoginResponse {
    private User user;
    private String token;

    public LoginResponse(User user, String token) {
        this.user = user;
        this.token = token;
    }

    public User getUser() { return user; }

    public String getToken() { return token; }
}
