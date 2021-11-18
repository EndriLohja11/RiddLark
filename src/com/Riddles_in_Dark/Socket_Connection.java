package com.Riddles_in_Dark;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class represents a single thread which will be given to each client connected to the server at a particular time
 * All call and responses made between the client and server are handled here on the server side
 */
public class Socket_Connection implements Runnable{
    private static ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();
    private Socket connection;  // Create Socket
    private ObjectInputStream clientCall;
    private ObjectOutputStream serverResponse;
    private User user;

    public Socket_Connection(Socket s) {
        this.connection = s;
    }

    @Override
    public void run() {
        try {
            while(true) {
                clientCall = new ObjectInputStream(connection.getInputStream()); //Create a call Buffer
                Call call = (Call) clientCall.readObject(); //Read Client call, Convert it to String
                System.out.println("Client sent : " + call.toString()); //Print the client call
                handleCall(call);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("connection closed");
//                e.printStackTrace();
            this.closeSocket();
        }
    }

    /**
     * close the socket  if the user disconnects
     */
    private void closeSocket() {
        try {
            if(clientCall != null) clientCall.close();
            if(serverResponse != null) serverResponse.close();
            if(connection != null) connection.close();
        } catch (IOException e) {
            System.out.println("Couldn't close server");
//            e.printStackTrace();
        }

    }

    /**
     * Handles all call made from client to server
     * @param call
     * @throws IOException
     */
    private void handleCall(Call call) throws IOException {
        String operation = call.getOperation();
        switch (operation) {
            case "register":
                this.register(call);
                break;
            case "login":
                this.login(call);
                break;
        }
    }

    /**
     * This function can be used to perform server side register user functionality
     */
    private synchronized void register(Call call) throws IOException {

        String username = call.getUsername();
        String password = call.getPassword();
        boolean freeUserName = true;
        for(int i =  1; i <= users.size(); i++) {
            if(users.isEmpty() || call == null) {
                break;
            }
            User _user = users.get(i);
            String userName = _user.getUsername();
            if(userName.equals(username)) {
                freeUserName = false;
                break;
            }
        }
        if (freeUserName) {
            User user = new User(username, password);
            
            this.notifyClient("Successfully registered", null, null, "login", connection);
        } else {
            this.notifyClient("The username is taken", null, null, "retry", connection);
        }
    }

    /**
     * This function can be used to perform server side login user functionality
     */
    private synchronized void login(Call call) throws IOException {
        String password = call.getPassword();
        String username = call.getUsername();
        for(int i =  1; i <= users.size(); i++) {
            if(users.isEmpty() || call == null) {
                break;
            }
            User _user = users.get(i);
            String userPassword = _user.getPassword();
            String userName = _user.getUsername();
            if(userName.equals(username) && userPassword.equals(password)) {
                user = _user;
                break;
            }
        }
    }


    /**
     * Sends all responses from server to client
     * @param response
     */
    private void notifyClient(String response, User user, Object responseData, String nextOperation, Socket connection) {
        try {
            Response response1 = new Response(response, user, responseData, nextOperation);
            serverResponse = new ObjectOutputStream(connection.getOutputStream()); //Create a Response Buffer
            serverResponse.writeObject(response1); //write "Response" in the outputStream
            serverResponse.flush(); //Send written content to client
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    }



