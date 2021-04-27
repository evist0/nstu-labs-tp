package com.evist0.common.simulation.middlewares;

import com.evist0.client.models.AppModel;
import com.evist0.common.entities.Entity;
import com.evist0.common.factories.TaxpayerFactory;

import java.util.Random;

public class FactoryMiddleware implements SimulationMiddleware {
    private final TaxpayerFactory _factory;
    private final Random _random = new Random();
    private long previousSpawnTimeN1 = 0;
    private long previousSpawnTimeN2 = 0;

    public FactoryMiddleware(TaxpayerFactory factory) {
        _factory = factory;
    }

    @Override
    public void onTick(AppModel model, float deltaTime) {
        var timePassed = model.getTimePassed();

        if (timePassed - previousSpawnTimeN1 >= model.getN1()) {
            Entity taxpayer = null;

            var P1 = model.getP1();
            var random = _random.nextDouble();

            if (random <= P1) {
                taxpayer = _factory.produceIndividual(model);
                previousSpawnTimeN1 = timePassed;
            }

            if (taxpayer != null) {
                var generated = model.getIndividualGenerated();

                model.setIndividualGenerated(generated + 1);
                model.addTaxpayer(taxpayer);
            }
        }

        if (timePassed - previousSpawnTimeN2 >= model.getN2()) {
            Entity taxpayer = null;

            var P2 = model.getP2();
            var random = _random.nextDouble();

            if (random <= P2) {
                taxpayer = _factory.produceCompany(model);
                previousSpawnTimeN2 = timePassed;
            }

            if (taxpayer != null) {
                var generated = model.getCompanyGenerated();

                model.setCompanyGenerated(generated + 1);
                model.addTaxpayer(taxpayer);
            }
        }
    }
}
