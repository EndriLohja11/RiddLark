package com.Riddles_in_Dark;

import java.io.IOException;
import java.net.Socket;

public class Main {
    /**
     * Entry point of client side of application
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Welcome");
        Socket connection = connect();
        if(connection != null) {
            Client client = new Client(connection);
            Interface Interface = new Interface(client, connection);
            System.out.println("Connected to server");
            Thread clientInput = new Thread(new com.programming_distributed_systems_project.Client_Input(connection, Interface));
            clientInput.start();
            Interface.loggedOutInterface();
        } else {
            System.out.println("You have no connection to the server. Please start server before running client");
        }
    }
    /**
     * Connects the client to the server
     */
    private static Socket connect() {
        int port = 1246; //  port number
        String ip = "localhost"; // localhost ip address = 127.0.0.1
        try {
            Socket connection = new Socket(ip, port); //Create a Client Socket for "localhost" address and port
            return connection;
        } catch(IOException e) {
//            e.printStackTrace();
            return null;
        }
    }
}
