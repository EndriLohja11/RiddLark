package com.Riddles_in_Dark;

import java.io.Serializable;

/**
 * This class represents each user in users stored on the server
 * Basically tells java properties and methods availabe to a user
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;


    public User (String username, String password) {

        this.username = username;
        this.password = password;

    }

    public String getPassword() {
        return password;
    }
    public String getUsername() {
        return username;
    }
    private int userId;
    private Integer teamId;
    public User (String username, String password, int userId) {
        this.username = username;
        this.password = password;
        this.userId = userId;
    }

    public static void main(String[] args) {
        User user = new User("User1", "Pass1", 1);
    }


}
