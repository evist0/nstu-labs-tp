package com.evist0.common.simulation.middlewares;

import com.evist0.client.models.AppModel;
import com.evist0.common.entities.Entity;

import java.util.Vector;

public class DestroyMiddleware implements SimulationMiddleware {
    @Override
    public void onTick(AppModel model, float deltaTime) {
        var currentTime = model.getTimePassed();
        var taxpayers = model.getTaxpayers();
        var toBeRemoved = new Vector<Entity>();

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
