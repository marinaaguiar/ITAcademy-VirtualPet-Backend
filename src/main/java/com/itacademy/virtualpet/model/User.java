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


    public User(String username, String password, List<String> petIds) {
        this.username = username;
        this.password = password;
        this.petIds = petIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getPetIds() {
        return petIds;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPetIds(List<String> petIds) {
        this.petIds = petIds;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", petIds=" + petIds +
                '}';
    }
}
