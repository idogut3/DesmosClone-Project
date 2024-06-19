package com.example.desmosclonejavafirst.entities;

public class User extends Entity {
    protected String firstName;
    protected String lastName;

    protected String email;
    protected String imageUrl;


    public User(String username, String password, String firstName, String lastName, String email, String imageUrl) {
        super(username, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.imageUrl = imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}