package com.evist0.simulation.middlewares;

import com.evist0.application.AppModel;
import com.evist0.taxpayer.AbstractTaxpayer;

import java.util.Vector;

public class DestroyMiddleware implements SimulationMiddleware {
    @Override
    public void onTick(AppModel model) {
        var currentTime = model.getTimePassed();
        var taxpayers = model.getTaxpayers();

        var toBeRemoved = new Vector<AbstractTaxpayer>();

        taxpayers.forEach(taxpayer -> {
            var timestamp = taxpayer.getTimestamp();
            var ttl = taxpayer.getTtl();

            if (timestamp + ttl <= currentTime) {
                toBeRemoved.add(taxpayer);
            }
        });

        toBeRemoved.forEach(model::removeTaxpayer);
    }
}
