package com.evist0.client.config;

import com.evist0.client.models.AppModel;

import java.io.*;
import java.util.Arrays;

public class Config {
    private final File _configFile;

    private Boolean started;
    private Long timePassed;

    private Long N1, N2;
    private Float P1, P2;
    private Long individualTtl, companyTtl;

    private Boolean individualMove;
    private Boolean companyMove;

    public Config(String configPath) {
        _configFile = new File(configPath);

        if (!_configFile.exists()) {
            try {
                _configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                var fileReader = new FileReader(_configFile);
                var bufferedReader = new BufferedReader(fileReader);

                String line = bufferedReader.readLine();
                while (line != null) {
                    var splitted = Arrays.stream(line.split("="))
                            .map(String::trim)
                            .map(String::toUpperCase)
                            .toList();

                    var token = splitted.get(0);
                    var value = splitted.get(1);

                    switch (token) {
                        case "STARTED" -> setStarted(value);
                        case "TIME_PASSED" -> setTimePassed(value);
                        case "N1" -> setN1(value);
                        case "N2" -> setN2(value);
                        case "P1" -> setP1(value);
                        case "P2" -> setP2(value);
                        case "INDIVIDUAL_TTL" -> setIndividualTtl(value);
                        case "COMPANY_TTL" -> setCompanyTtl(value);
                        case "INDIVIDUAL_MOVE" -> setIndividualMove(value);
                        case "COMPANY_MOVE" -> setCompanyMove(value);
                    }

                    line = bufferedReader.readLine();
                }

                bufferedReader.close();
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void save(AppModel model) throws Exception {
        var fileWriter = new FileWriter(_configFile);
        var bufferedWriter = new BufferedWriter(fileWriter);

        write("STARTED", model.getStarted() ? "TRUE" : "FALSE", bufferedWriter);
        write("TIME_PASSED", Long.toString(model.getTimePassed()), bufferedWriter);
        write("N1", Long.toString(model.getN1()), bufferedWriter);
        write("N2", Long.toString(model.getN2()), bufferedWriter);
        write("P1", Float.toString(model.getP1()), bufferedWriter);
        write("P2", Float.toString(model.getP2()), bufferedWriter);
        write("INDIVIDUAL_TTL", Long.toString(model.getIndividualTtl()), bufferedWriter);
        write("COMPANY_TTL", Long.toString(model.getCompanyTtl()), bufferedWriter);
        write("INDIVIDUAL_MOVE", model.getIndividualMove() ? "TRUE" : "FALSE", bufferedWriter);
        write("COMPANY_MOVE", model.getCompanyMove() ? "TRUE" : "FALSE", bufferedWriter);

        bufferedWriter.close();
        fileWriter.close();
    }

    public Boolean getStarted() {
        return started;
    }

    private void setStarted(String value) {
        started = value.equals("TRUE");
    }

    public Long getTimePassed() {
        return timePassed;
    }

    private void setTimePassed(String value) {
        timePassed = Long.parseLong(value);
    }

    public Long getN1() {
        return N1;
    }

    private void setN1(String value) {
        N1 = Long.parseLong(value);
    }

    public Long getN2() {
        return N2;
    }

    private void setN2(String value) {
        N2 = Long.parseLong(value);
    }

    public Float getP1() {
        return P1;
    }

    private void setP1(String value) {
        P1 = Float.parseFloat(value);
    }

    public Float getP2() {
        return P2;
    }

    private void setP2(String value) {
        P2 = Float.parseFloat(value);
    }

    public Long getIndividualTtl() {
        return individualTtl;
    }

    private void setIndividualTtl(String value) {
        individualTtl = Long.parseLong(value);
    }

    public Long getCompanyTtl() {
        return companyTtl;
    }

    private void setCompanyTtl(String value) {
        companyTtl = Long.parseLong(value);
    }

    public Boolean getIndividualMove() {
        return individualMove;
    }

    private void setIndividualMove(String value) {
        individualMove = value.equals("TRUE");
    }

    public Boolean getCompanyMove() {
        return companyMove;
    }

    private void setCompanyMove(String value) {
        companyMove = value.equals("TRUE");
    }

    private void write(String token, String value, BufferedWriter bufferedWriter) throws IOException {
        bufferedWriter.write("%s=%s\n".formatted(token, value));
    }
}
