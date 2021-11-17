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
            ArrayList<String> user = LoginOrRegisterInfo();
            Request loginRequest = new Request(user.get(0),user.get(1), "login");
            ClientOutputThread clientOutputThread = new ClientOutputThread(connection, loginRequest);
            Thread thread = new Thread(clientOutputThread);
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
            ArrayList<String> user = LoginOrRegisterInfo();
            Request registerRequest = new Request(user.get(0),user.get(1), "register");
            ClientOutputThread clientOutputThread = new ClientOutputThread(connection, registerRequest);
            Thread thread = new Thread(clientOutputThread);
            thread.start();
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }

    /**
     * Get user details for login or register
     * @return ArrayList<String> of user details
     * */
    public ArrayList<String> LoginOrRegisterInfo() throws IOException{
        String username;
        String password;
        ArrayList<String> user = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        while (!Thread.interrupted()) {
            System.out.println("Enter username: ");
            username = scanner.next();
            if (isValidUserNameOrPassword(username)) {
                user.add(username);
                break;
            } else {
                printInvalidUsernameOrPassword("username");
            }
        }

        while(!Thread.interrupted()) {
            System.out.println("Enter password: ");
            password = scanner.next();
            if (isValidUserNameOrPassword(password)) {
                user.add(password);
                break;
            } else {
                printInvalidUsernameOrPassword("password");
            }
        }
        return user;
    }

    /**
     * Informs the user of an invalid command entered
     * @param argument command the user entered
     */
    public static void printInvalidUsernameOrPassword(String argument) {
        System.out.println(argument + " must have at least 6 characters ");
    }

    /**
     * Checks if the user entered an available username or password
     * @param argument the detail which the user entered
     * @return boolean, true if valid and false is if invalid
     */
    public static boolean isValidUserNameOrPassword(String argument) {
        if(argument.length() >= 6) {
            return true;
        } else {
            return false;
        }
    }
}
