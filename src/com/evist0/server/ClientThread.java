package com.evist0.server;

import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread {
    private final Server server;
    private final Socket clientSocket;

    private PrintWriter writer;

    public ClientThread(Socket socket, Server server) {
        this.clientSocket = socket;
        this.server = server;
    }

    public void run() {
        try {
            InputStream input = clientSocket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = clientSocket.getOutputStream();
            writer = new PrintWriter(output, true);

            printUsers();

            String serverMessage = "New user connected: " + clientSocket.getInetAddress();
            server.broadcast(serverMessage, this);

            String clientMessage;

            do {
                clientMessage = reader.readLine();
                System.out.println(clientMessage);
                server.broadcast(clientMessage, this);
            } while (!clientMessage.equals("bye"));

            server.removeUser(this);
            clientSocket.close();

            serverMessage = clientSocket.getInetAddress() + " has quitted.";
            server.broadcast(serverMessage, this);

        } catch (IOException ex) {
            System.out.println("Error in UserThread: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    void printUsers() {
        if (server.hasUsers()) {
            writer.println("Connected users:\n");

            var threads = server.getClientThreads();

            threads.forEach(thread -> {
                if (thread != this) {
                    var clientSocket = thread.getClientSocket();
                    writer.println(clientSocket.getInetAddress() + "\n");
                }
            });
        } else {
            writer.println("No other users connected");
        }
    }

    void sendMessage(String message) {
        writer.println(message);
    }

    public Socket getClientSocket() {
        return clientSocket;
    }
}