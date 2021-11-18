package com.Riddles_in_Dark;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class contains  all threads  on the server at a specific time
 */
public class Server_Threads {

    private static Socket connection; //Create Socket
    private static ServerSocket serverSocket; //Create a Server Socket
    private static ExecutorService Serverthreads = Executors.newFixedThreadPool(15); //Create threads
    public static void main(String[] args) throws IOException {
        serverSocket = new ServerSocket(1246); //Start a new server socket on port 1246
        while (true) {
            connection = serverSocket.accept();//Accept when a call arrives
            Socket_Connection serverTask = new Socket_Connection(connection);//Start a task Thread to handle client call
            Serverthreads.execute(serverTask);//Execute Thread
        }
    }
}
