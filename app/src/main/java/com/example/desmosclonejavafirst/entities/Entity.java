package com.example.desmosclonejavafirst.entities;

/**
 * Abstract base class representing an entity with common attributes.
 */
public abstract class Entity {


    protected String username;
    protected String password;


    /**
     * Constructs a new entity with the specified username and password.
     *
     * @param username The username of the entity.
     * @param password The password of the entity.
     */
    public Entity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the username of the entity.
     *
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password of the entity.
     *
     * @return The password.
     */
    public String getPassword() {
        return password;
    }


}
