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

            String serverMessage = "[print]New user connected " + clientSocket.getInetAddress();
            server.broadcast(serverMessage, this);

            String clientMessage;

            do {
                clientMessage = reader.readLine();
                System.out.println(clientMessage);
                server.broadcast(clientMessage, this);
            } while (!clientMessage.equals("[disconnect]"));

            server.removeUser(this);
            clientSocket.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    void printUsers() {
        StringBuilder sb = new StringBuilder();

        if (!server.isEmpty()) {
            sb.append("[print]Connected users: ");

            var threads = server.getClientThreads();

            threads.forEach(thread -> {
                if (thread != this) {
                    var clientSocket = thread.getClientSocket();
                    sb.append(clientSocket.getInetAddress()).append(",");
                }
            });

            sb.append("\n");
        } else {
            sb.append("[print]No other users connected");
        }

        writer.println(sb);
    }

    void sendMessage(String message) {
        writer.println(message);
    }

    public Socket getClientSocket() {
        return clientSocket;
    }
}