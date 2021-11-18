package com.programming_distributed_systems_project;

import com.Riddles_in_Dark.Response;
import com.Riddles_in_Dark.Interface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client_Input implements Runnable {
    private Socket connection;
    private ObjectInputStream inputStream;
    private Interface Interface;
    private ExecutorService Serverthreads = Executors.newFixedThreadPool(5); // a multiple threads

    public Client_Input(Socket connection, Interface Interface) {
        this.connection = connection;
        this.Interface = Interface;

    }

    @Override
    public void run() {
        try {
            handleResponses();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Handle all responses sent from server to client
     */
    public void handleResponses() {
        GameOver: while(!Serverthreads.isTerminated() && !Serverthreads.isShutdown()) {
            try {
                inputStream = new ObjectInputStream(connection.getInputStream());
                Response response = (Response) inputStream.readObject(); //Read Server Reply
                String nextOperation = response.nextOperation();
                Object responseInfo = response.getResponseInfo();
                System.out.println(response.getResponse());
                switch (nextOperation) {
                    case "retry": {
                        Serverthreads.execute(() -> Interface.loggedOutInterface());
                        break;
                    }
                    case "login": {
                        System.out.println(" You are registered, log in to manage your account");
                        Serverthreads.execute(() -> Interface.loggedOutInterface());
                        break;
                    }
                    case "game over": {
                        Serverthreads.shutdownNow();
                        break GameOver;
                    }
                }
            } catch(ClassNotFoundException | IOException e ) {
                System.out.println("connection to server has been lost");
//                e.printStackTrace();
                break;
            }
        }
    }

}
