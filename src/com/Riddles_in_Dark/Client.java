package com.Riddles_in_Dark;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    private  static Scanner scanner = new Scanner(System.in);
    private User user = null;
    private Socket connection;

    public Client(Socket connection) {
        this.connection = connection;
    }

    /**
     * Client login
     */
    public void login() {
        try {
            ArrayList<String> user = Login_RegisterInfo();
            Call loginCall = new Call(user.get(0),user.get(1), "login");
            Client_Output Client_Output = new Client_Output(connection, loginCall);
            Thread thread = new Thread(Client_Output);
            thread.start();
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }

    /**
     * Client register
     */
    public void register() {
        try {
            ArrayList<String> user = Login_RegisterInfo();
            Call registerCall = new Call(user.get(0),user.get(1), "register");
            Client_Output client_Output = new Client_Output(connection, registerCall);
            Thread thread = new Thread(client_Output);
            thread.start();
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }

    /**
     * Get user information for login or register
     * @return ArrayList<String> of user details
     * */
    public ArrayList<String> Login_RegisterInfo() throws IOException{
        String username;
        String password;
        ArrayList<String> user = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        while (!Thread.interrupted()) {
            System.out.println("Enter username: ");
            username = scanner.next();
            if (ValidUserNameOrPassword(username)) {
                user.add(username);
                break;
            } else {
                InvalidUsernameOrPassword("username");
            }
        }

        while(!Thread.interrupted()) {
            System.out.println("Enter password: ");
            password = scanner.next();
            if (ValidUserNameOrPassword(password)) {
                user.add(password);
                break;
            } else {
                InvalidUsernameOrPassword("password");
            }
        }
        return user;
    }

    /**
     * Informs the user of an invalid command entered
     * @param argument command the user entered
     */
    public static void InvalidUsernameOrPassword(String argument) {
        System.out.println(argument + " must have at least 6 characters ");
    }

    /**
     * Checks if the user entered an available username or password
     */
    public static boolean ValidUserNameOrPassword(String argument) {
        if(argument.length() >= 6) {
            return true;
        } else {
            return false;
        }
    }
}

