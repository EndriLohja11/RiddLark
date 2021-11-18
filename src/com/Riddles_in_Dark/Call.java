package com.Riddles_in_Dark;


import java.io.Serializable;

/**
 * This class represents all messages sent from client to server
 */
public class Call implements Serializable {
    private static final long serialVersionUID = 1L;
    private String call;
    private String operation;
    private String username;
    private String password;



    /**
     * This call is  created when the user logs in or signs up
     * Login call constructor
     * @param username
     * @param password
     * @param operation
     */
    public Call(String username, String password, String operation) {
        super();
        this.operation = operation;
        this.username = username;
        this.password = password;
    }


    public String getOperation() {
        return operation;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

}
