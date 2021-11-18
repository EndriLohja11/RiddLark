package com.Riddles_in_Dark;

import java.io.Serializable;

/**
 * This class represents each user in users stored on the server
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




}


