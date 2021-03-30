package com.evist0.dto.settings;

public class SettingsDTO {
    public final Long N1, N2;
    public final float P1, P2;
    public final Long individualTtl, companyTtl;

    public SettingsDTO(Long N1, Long N2, float P1, float P2, Long individualTtl, Long companyTtl) {
        this.N1 = N1;
        this.N2 = N2;
        this.P1 = P1;
        this.P2 = P2;
        this.individualTtl = individualTtl;
        this.companyTtl = companyTtl;
    }
}
