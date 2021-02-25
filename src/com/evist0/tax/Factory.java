package com.evist0.tax;

import com.evist0.models.Model;

import java.util.Random;

public class Factory {
    private final Random _r;
    private final Model _m;

    public Factory(Model model) {
        _r = new Random();
        _m = model;
    }

    public IndividualTaxpayer produceIndividual() {
        var P1 = _m.get_P1();

        var generated = _r.nextDouble();

        if (generated <= P1) {
            return new IndividualTaxpayer();
        } else {
            return null;
        }
    }

    public CompanyTaxpayer produceCompany() {
        var P2 = _m.get_P2();

        var generated = _r.nextDouble();

        if (generated <= P2) {
            return new CompanyTaxpayer();
        } else {
            return null;
        }
    }
}
