package com.evist0.client;

import com.evist0.client.models.AppModel;
import com.evist0.client.views.console.ConsoleView;
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

    private Client(ConsoleView console, AppModel model, InetAddress inetAddress, int port) throws IOException {

        server = new Socket(inetAddress, port);

        var outputStream = server.getOutputStream();
        output = new PrintWriter(outputStream, true);

        output.println("[connect]");

        ExecutorService readThread = Executors.newSingleThreadExecutor();

        readThread.execute(() -> {
            try {
                InputStream input = server.getInputStream();

                var reader = new BufferedReader(new InputStreamReader(input));

                while (!server.isClosed()) {
                    String received = reader.readLine();

                    if (received.startsWith("[print]")) {
                        String message = received.substring(7);
                        console.print(message);
                    }
                    if (received.startsWith("[set]")) {
                        String setCommand = received.substring(5);

                        Property property = Property.valueOf(setCommand.split(" ")[0]);
                        String value = setCommand.split(" ")[1];

                        switch (property) {
                            case N1 -> model.setN1(Long.parseLong(value), false);
                            case N2 -> model.setN2(Long.parseLong(value), false);
                            case P1 -> model.setP1(Float.parseFloat(value), false);
                            case P2 -> model.setP2(Float.parseFloat(value), false);
                            case IndividualTtl -> model.setIndividualTtl(Long.valueOf(value), false);
                            case CompanyTtl -> model.setCompanyTtl(Long.valueOf(value), false);
                            case TimerVisibility -> model.setTimerVisible(Boolean.parseBoolean(value), false);
                            case ResultsVisibility -> model.setDialogVisible(Boolean.parseBoolean(value), false);
                            case IndividualMove -> model.setIndividualMove(Boolean.parseBoolean(value), false);
                            case CompanyMove -> model.setCompanyMove(Boolean.parseBoolean(value), false);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void connect(ConsoleView console, AppModel model, InetAddress inetAddress, int port) throws IOException {
        if (instance == null) {
            instance = new Client(console, model, inetAddress, port);
        }
    }

    public static Client getInstance() {
        return instance;
    }

    public static void disconnect() throws IOException {
        if (instance == null) {
            return;
        }

        instance.sendMessage("[disconnect]");
        instance.server.close();

        instance = null;
    }

    public void sendMessage(String message) throws IOException {
        output.println(message);
    }
}
