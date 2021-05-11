package com.evist0.client;

import com.evist0.client.models.AppModel;
import com.evist0.common.properties.Property;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    private static Client instance;

    private final Socket server;
    private final PrintWriter output;
    private final AppModel model;

    private Client(AppModel model, InetAddress inetAddress, int port) throws IOException {
        this.model = model;
        server = new Socket(inetAddress, port);

        var outputStream = server.getOutputStream();
        output = new PrintWriter(outputStream, true);

        output.println("Connected");

        ExecutorService readThread = Executors.newSingleThreadExecutor();

        readThread.execute(() -> {
            try {
                InputStream input = server.getInputStream();

                var reader = new BufferedReader(new InputStreamReader(input));

                while (!server.isClosed()) {
                    String received = reader.readLine();

                    if (received.startsWith("[set]")) {
                        String setCommand = received.substring(5);

                        Property property = Property.valueOf(setCommand.split(" ")[0]);
                        String value = setCommand.split(" ")[1];

                        switch (property) {
                            case N1 -> model.setN1(Long.parseLong(value));
                            case N2 -> model.setN2(Long.parseLong(value));
                            case P1 -> model.setP1(Float.parseFloat(value));
                            case P2 -> model.setP2(Float.parseFloat(value));
                            case IndividualTtl -> model.setIndividualTtl(Long.valueOf(value));
                            case CompanyTtl -> model.setCompanyTtl(Long.valueOf(value));
                            case TimerVisibility -> model.setTimerVisible(Boolean.parseBoolean(value));
                            case ResultsVisibility -> model.setDialogVisible(Boolean.parseBoolean(value));
                            case IndividualMove -> model.setIndividualMove(Boolean.parseBoolean(value));
                            case CompanyMove -> model.setCompanyMove(Boolean.parseBoolean(value));
                        }
                    }

                    System.out.println(received);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static Client connect(AppModel model, InetAddress inetAddress, int port) throws IOException {
        if (instance == null) {
            instance = new Client(model, inetAddress, port);
        }

        return instance;
    }

    public static Client getInstance()
    {
        return instance;
    }

    public void disconnect() throws IOException {
        output.println("Disconnected");

        server.close();

        instance = null;
    }

    public void sendMessage(String message) throws IOException {
        output.println(message);
    }
}
