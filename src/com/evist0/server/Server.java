package com.evist0.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

class Server {
    private final int port;
    private final Set<ClientThread> clientThreads = new HashSet<>();

    public Server(int port) {
        this.port = port;
    }

    public void execute() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();

                ClientThread newClient = new ClientThread(clientSocket, this);

                newClient.start();
                clientThreads.add(newClient);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int port = 1337;

        Server server = new Server(port);
        server.execute();
    }

    void broadcast(String message, ClientThread excludeClient) {
        for (ClientThread client : clientThreads) {
            if (client != excludeClient) {
                client.sendMessage(message);
            }
        }
    }

    void removeUser(ClientThread aUser) {
        clientThreads.remove(aUser);
    }

    boolean isEmpty() {
        return this.clientThreads.isEmpty();
    }

    Set<ClientThread> getClientThreads() {
        return clientThreads;
    }
}

