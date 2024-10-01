package com.itacademy.virtualpet.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "users")
public class User {

    @Id
    private String id;
    private String username;
    private String password;
    @Field("pets")
    private List<String> petIds;
    @Field("isAdmin")
    private boolean isAdmin;

    public User(String username, String password, List<String> petIds, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.petIds = petIds;
        this.isAdmin = isAdmin;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public void setAdmin(boolean isAdmin) { this.isAdmin = isAdmin; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }

    public List<String> getPetIds() { return petIds; }

    public boolean isAdmin() { return isAdmin; }

    public void setPassword(String password) { this.password = password; }

    public void setPetIds(List<String> petIds) { this.petIds = petIds; }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", petIds='" + petIds + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
