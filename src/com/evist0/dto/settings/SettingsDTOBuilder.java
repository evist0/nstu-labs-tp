package com.evist0.dto.settings;

public class SettingsDTOBuilder {
    private int N1, N2;
    private float P1, P2;

    public SettingsDTOBuilder() {
        this.N1 = 0;
        this.N2 = 0;

        this.P1 = 0;
        this.P2 = 0;
    }

    public SettingsDTOBuilder setN1(String N1) throws NumberFormatException {
        this.N1 = Integer.parseInt(N1);

        return this;
    }

    public SettingsDTOBuilder setN2(String N2) throws NumberFormatException {
        this.N2 = Integer.parseInt(N2);

        return this;
    }

    public SettingsDTOBuilder setP1(float P1) {
        this.P1 = P1;

        return this;
    }

    public SettingsDTOBuilder setP2(float P2) {
        this.P2 = P2;

        return this;
    }

    public SettingsDTO build() {
        return new SettingsDTO(N1, N2, P1, P2);
    }
}
