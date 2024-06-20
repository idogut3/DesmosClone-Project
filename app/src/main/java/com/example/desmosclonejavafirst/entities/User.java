package com.example.desmosclonejavafirst.entities;

/**
 * Represents a user entity extending from Entity, which provides basic username and password fields.
 */
public class User extends Entity {
    protected String firstName;
    protected String lastName;

    protected String email;
    protected String imageUrl;


    /**
     * Constructor to initialize a User object.
     *
     * @param username  The username of the user.
     * @param password  The password of the user.
     * @param firstName The first name of the user.
     * @param lastName  The last name of the user.
     * @param email     The email address of the user.
     * @param imageUrl  The URL of the user's profile image.
     */
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