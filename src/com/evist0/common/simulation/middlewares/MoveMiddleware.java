package com.evist0.common.simulation.middlewares;

import com.evist0.client.models.AppModel;
import com.evist0.common.properties.Property;
import com.evist0.common.entities.CompanyTaxpayer;
import com.evist0.common.entities.IndividualTaxpayer;

public class MoveMiddleware implements SimulationMiddleware {
    public void onTick(AppModel model, float deltaTime) {
        model.getTaxpayers().forEach(taxpayer -> {
            if (taxpayer.isMoving()) {
                if (taxpayer instanceof IndividualTaxpayer && model.getIndividualMove()) {
                    taxpayer.doMoveTask(deltaTime);
                }

                if (taxpayer instanceof CompanyTaxpayer && model.getCompanyMove()) {
                    taxpayer.doMoveTask(deltaTime);
                }
            }
        });

        model.notifyListeners(Property.Taxpayers, model.getTaxpayers());
    }
}
