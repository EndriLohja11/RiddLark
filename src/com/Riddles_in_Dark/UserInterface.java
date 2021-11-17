package com.Riddles_in_Dark;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

/**
 * This class handles all stuff which involves a user interface
 * This is used mainly as main menu and fallback in case the suddenly user leaves all application flow
 */
public class UserInterface {
    private static BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
    private Client client;
    private Socket connection;

    public UserInterface(Client client, Socket connection) {
        this.client = client;
        this.connection = connection;
    }

    /**
     * This will print out different steps for a logged out user to follow
     * It will automatically load other classes and move users to new application flows
     */
    public void loggedOutInterface() {
        printExitInfo();
        System.out.println("What do you want to do? Enter number of command");
        System.out.println("1. Login");
        System.out.println("2. Register");
        Scanner scanner = new Scanner(System.in);

        outside: while(!Thread.interrupted()) {
            String input = scanner.next();
            if(isQuit(input)) {
                printThanks();
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    int command = new Integer(input);
                    switch (command) {
                        case 1:
                            client.login();
                            break outside;
                        case 2:
                            client.register();
                            break outside;
                        default:
                            throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                    printUnknownCommand();
                }
            }
        }
    }

    /**
     * Checks if the user entered q to quit the application
     * @param command
     * @return true if the user entered q or false otherwise
     */
    public static boolean isQuit(String command) {
        return "exit".equals(command);
    }

    /**
     * Tell the user he entered a command which is not among the current valid list of commands
     */
    public static void printUnknownCommand() {
        System.out.println("That is not a valid command, please check command list");
    }

    /**
     * Show an info message on what the user should do if he wants to quit the application
     */
    public static void printExitInfo() {
        System.out.println("INFO: Enter `exit` to quit");
    }

    /**
     * Print a simple thanks message on terminating the application
     */
    public static void printThanks() {
        System.out.println("Thanks for playing...");
    }

    public static String newLine() {
        return System.getProperty("line.separator");//This will retrieve line separator dependent on OS.
    }

}
