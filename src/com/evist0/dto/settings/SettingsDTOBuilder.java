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

    public SettingsDTOBuilder setN1(String N1) throws SettingsException {
        int value;

        try {
            value = Integer.parseInt(N1);
        } catch (Exception error) {
            throw new SettingsException(SettingsExceptionField.N1, "N1 contains wrong Value");
        }

        if (value <= 0) {
            throw new SettingsException(SettingsExceptionField.N1, "N1 can't be less then 0");
        }

        this.N1 = value;
        return this;
    }

    public SettingsDTOBuilder setN2(String N2) throws SettingsException {
        int value;

        try {
            value = Integer.parseInt(N2);
        } catch (Exception error) {
            throw new SettingsException(SettingsExceptionField.N2, "N2 contains wrong Value");
        }

        if (value <= 0) {
            throw new SettingsException(SettingsExceptionField.N2, "N2 can't be less then 0");
        }

        this.N2 = value;
        return this;
    }

    public SettingsDTOBuilder setP1(Float P1) {
        this.P1 = P1;

        return this;
    }

    public SettingsDTOBuilder setP2(Float P2) {
        this.P2 = P2;

        return this;
    }

    public SettingsDTO build() {
        return new SettingsDTO(N1, N2, P1, P2);
    }
}
