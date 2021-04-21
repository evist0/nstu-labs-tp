package com.evist0.simulation.middlewares;

import com.evist0.application.AppModel;

public interface SimulationMiddleware {
    void onTick(AppModel model, float deltaTime);
}
