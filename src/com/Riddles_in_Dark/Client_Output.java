package com.Riddles_in_Dark;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client_Output implements Runnable {
    private Socket connection;
    private ObjectOutputStream outputStream;
    private Call call;

    public Client_Output(Socket connection, Call call) {
        this.connection = connection;
        this.call = call;
    }

    @Override
    public void run() {
        try {
            sendRequest(call);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    /**
     * Handle all call made by client to server
     * @param call
     */
    public void sendRequest(Call call) {
        try {
            System.out.println("Connecting to server");
            outputStream = new ObjectOutputStream(connection.getOutputStream());
            outputStream.writeObject(call);
            outputStream.flush();
        } catch (IOException e) {
            System.out.println("Cannot create a connection to the server...");
            e.printStackTrace();
        }
    }

}

