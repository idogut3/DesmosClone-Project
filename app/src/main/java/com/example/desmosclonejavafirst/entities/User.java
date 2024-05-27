package com.example.desmosclonejavafirst.entities;

import com.example.desmosclonejavafirst.entities.Entity;

public class User extends Entity {
    protected String firstName;
    protected String lastName;

    protected String email;


    public User(String username, String password, String firstName, String lastName, String email) {
        super(username, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }


    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


}