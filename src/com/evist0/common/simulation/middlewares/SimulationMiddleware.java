package com.evist0.common.simulation.middlewares;

import com.evist0.client.models.AppModel;

public interface SimulationMiddleware {
    void onTick(AppModel model, float deltaTime);
}
