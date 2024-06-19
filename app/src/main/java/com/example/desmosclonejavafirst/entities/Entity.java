package com.example.desmosclonejavafirst.entities;

public abstract class Entity {

    protected String username;
    protected String password;


    public Entity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


}
