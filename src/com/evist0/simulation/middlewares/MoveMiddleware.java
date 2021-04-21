package com.evist0.simulation.middlewares;

import com.evist0.application.AppModel;
import com.evist0.properties.Property;
import com.evist0.taxpayer.CompanyTaxpayer;
import com.evist0.taxpayer.IndividualTaxpayer;

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
