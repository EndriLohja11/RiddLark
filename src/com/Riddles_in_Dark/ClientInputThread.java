package com.programming_distributed_systems_project;

import com.Riddles_in_Dark.Reply;
import com.Riddles_in_Dark.UserInterface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientInputThread implements Runnable {
    private Socket connection;
    private ObjectInputStream inputStream;
    private UserInterface userInterface;
    private ExecutorService thPoolServer = Executors.newFixedThreadPool(5); // a pool of threads

    public ClientInputThread(Socket connection, UserInterface userInterface) {
        this.connection = connection;
        this.userInterface = userInterface;

    }

    @Override
    public void run() {
        try {
            handleReply();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Handle all replies sent from server to client
     */
    public void handleReply() {
        endGame: while(!thPoolServer.isTerminated() && !thPoolServer.isShutdown()) {
            try {
                inputStream = new ObjectInputStream(connection.getInputStream());
                Reply reply = (Reply) inputStream.readObject(); //Read Server Reply
                String nextOperation = reply.nextOperation();
                Object replyData = reply.getReplyData();
                System.out.println(reply.getResponse());
                switch (nextOperation) {
                    case "retry": {
                        thPoolServer.execute(() -> userInterface.loggedOutInterface());
                        break;
                    }
                    case "login": {
                        System.out.println(" You are registered, log in to manage your account");
                        thPoolServer.execute(() -> userInterface.loggedOutInterface());
                        break;
                    }
                    case "game over": {
                        thPoolServer.shutdownNow();
                        break endGame;
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
