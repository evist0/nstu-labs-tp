package com.evist0.common.dto.settings;

public class SettingsDTOBuilder {
    private Long N1, N2;
    private float P1, P2;
    private Long individualTtl, companyTtl;

    public SettingsDTOBuilder() {
        this.N1 = 0L;
        this.N2 = 0L;

        this.P1 = 0;
        this.P2 = 0;

        this.individualTtl = 0L;
        this.companyTtl = 0L;
    }

    public SettingsDTOBuilder setN1(String N1) throws SettingsException {
        long value;

        try {
            value = Long.parseLong(N1);
        } catch (Exception error) {
            throw new SettingsException(SettingsExceptionField.N1, "N1 contains wrong Value");
        }

        if (value < 0) {
            throw new SettingsException(SettingsExceptionField.N1, "N1 less then 0");
        }

        this.N1 = value;
        return this;
    }

    public SettingsDTOBuilder setN2(String N2) throws SettingsException {
        long value;

        try {
            value = Long.parseLong(N2);
        } catch (Exception error) {
            throw new SettingsException(SettingsExceptionField.N2, "N2 contains wrong Value");
        }

        if (value < 0) {
            throw new SettingsException(SettingsExceptionField.N2, "N2 less then 0");
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

    public SettingsDTOBuilder setIndividualTtl(String individualTtl) throws SettingsException {
        long value;

        try {
            value = Long.parseLong(individualTtl);
        } catch (Exception error) {
            throw new SettingsException(SettingsExceptionField.IndividualTtl, "Individual TTL contains wrong Value");
        }

        if (value < 0) {
            throw new SettingsException(SettingsExceptionField.IndividualTtl, "Individual TTL less then 0");
        }

        this.individualTtl = value;
        return this;
    }

    public SettingsDTOBuilder setCompanyTtl(String companyTtl) throws SettingsException {
        long value;

        try {
            value = Long.parseLong(companyTtl);
        } catch (Exception error) {
            throw new SettingsException(SettingsExceptionField.N1, "Company TTL contains wrong Value");
        }

        if (value < 0) {
            throw new SettingsException(SettingsExceptionField.CompanyTtl, "Company TTL less then 0");
        }


        this.companyTtl = value;
        return this;
    }

    public SettingsDTO build() {
        return new SettingsDTO(N1, N2, P1, P2, individualTtl, companyTtl);
    }
}
