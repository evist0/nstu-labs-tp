package com.evist0.simulation.middlewares;

import com.evist0.application.AppModel;
import com.evist0.taxpayer.AbstractTaxpayer;
import com.evist0.taxpayer.TaxpayerFactory;

import java.util.Random;

public class FactoryMiddleware implements SimulationMiddleware {
    private final Random _random = new Random();
    private final TaxpayerFactory _factory;

    public FactoryMiddleware(TaxpayerFactory factory) {
        _factory = factory;
    }

    @Override
    public void onTick(AppModel model) {
        var timePassed = model.getTimePassed();

        if (timePassed % model.getN1() == 0) {
            AbstractTaxpayer taxpayer = null;

            var P1 = model.getP1();
            var random = _random.nextDouble();

            if (random <= P1) {
                taxpayer = _factory.produceIndividual(model);
            }

            if (taxpayer != null) {
                var generated = model.getIndividualGenerated();

                model.setIndividualGenerated(generated + 1);
                model.addTaxpayer(taxpayer);
            }
        }

        if (timePassed % model.getN2() == 0) {
            AbstractTaxpayer taxpayer = null;

            var P2 = model.getP2();
            var random = _random.nextDouble();

            if (random <= P2) {
                taxpayer = _factory.produceCompany(model);
            }

            if (taxpayer != null) {
                var generated = model.getCompanyGenerated();

                model.setCompanyGenerated(generated + 1);
                model.addTaxpayer(taxpayer);
            }
        }
    }
}
