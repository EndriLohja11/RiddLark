package com.Riddles_in_Dark;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class represents a single thread which will be given to each client connected to the server at a particular time
 * All request and replies made between the client and server are handled here on the server side
 */
public class ServerSocketTask implements Runnable{
    private static ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();
    private Socket connection;  // Create Socket
    private ObjectInputStream clientRequest;
    private ObjectOutputStream serverReply;
    private User user;

    public ServerSocketTask(Socket s) {
        this.connection = s;
    }

    @Override
    public void run() {
        try {
            while(true) {
                clientRequest = new ObjectInputStream(connection.getInputStream()); //Create a Request Buffer
                Request request = (Request) clientRequest.readObject(); //Read Client request, Convert it to String
                System.out.println("Client sent : " + request.toString()); //Print the client request
                handleRequest(request);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("connection closed");
//                e.printStackTrace();
            this.killSocketTask();
        }
    }

    /**
     * Kill this socket task if the user disconnects
     */
    private void killSocketTask() {
        try {
            if(clientRequest != null) clientRequest.close();
            if(serverReply != null) serverReply.close();
            if(connection != null) connection.close();
        } catch (IOException e) {
            System.out.println("Couldn't kill server task");
//            e.printStackTrace();
        }

    }

    /**
     * Handles all request made from client to server
     * @param request
     * @throws IOException
     */
    private void handleRequest(Request request) throws IOException {
        String operation = request.getOperation();
        switch (operation) {
            case "register":
                this.register(request);
                break;
            case "login":
                this.login(request);
                break;
        }
    }

    /**
     * This function can be used to perform server side register user functionality
     */
    private synchronized void register(Request request) throws IOException {
        int userId = users.size() + 1;
        String reqUsername = request.getUsername();
        String reqPassword = request.getPassword();
        boolean freeUserName = true;
        for(int i =  1; i <= users.size(); i++) {
            if(users.isEmpty() || request == null) {
                break;
            }
            User _user = users.get(i);
            String userName = _user.getUsername();
            if(userName.equals(reqUsername)) {
                freeUserName = false;
                break;
            }
        }
        if (freeUserName) {
            User user = new User(reqUsername, reqPassword, userId);
            users.put(userId, user);
            this.notifyClient("Successfully registered", null, null, "login", connection);
        } else {
            this.notifyClient("The username is taken", null, null, "retry", connection);
        }
    }

    /**
     * This function can be used to perform server side login user functionality
     */
    private synchronized void login(Request request) throws IOException {
        String reqPassword = request.getPassword();
        String reqUsername = request.getUsername();
        for(int i =  1; i <= users.size(); i++) {
            if(users.isEmpty() || request == null) {
                break;
            }
            User _user = users.get(i);
            String userPassword = _user.getPassword();
            String userName = _user.getUsername();
            if(userName.equals(reqUsername) && userPassword.equals(reqPassword)) {
                user = _user;
                break;
            }
        }
    }


    /**
     * Sends all replies from server to client
     * @param response
     */
    private void notifyClient(String response, User user, Object responseData, String nextOperation, Socket connection) {
        try {
            Reply reply = new Reply(response, user, responseData, nextOperation);
            serverReply = new ObjectOutputStream(connection.getOutputStream()); //Create a Reply Buffer
            serverReply.writeObject(reply); //write "Reply" in the outputStream
            serverReply.flush(); //Send written content to client
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    }


