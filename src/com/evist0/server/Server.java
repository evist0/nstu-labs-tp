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

                clientThreads.add(newClient);
                newClient.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Syntax: java Server <port-number>");
            System.exit(0);
        }

        int port = Integer.parseInt(args[0]);

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
        System.out.println("The user " + aUser.getClientSocket().getInetAddress() + " quitted");
    }

    boolean hasUsers() {
        return !this.clientThreads.isEmpty();
    }

    Set<ClientThread> getClientThreads() {
        return clientThreads;
    }
}

