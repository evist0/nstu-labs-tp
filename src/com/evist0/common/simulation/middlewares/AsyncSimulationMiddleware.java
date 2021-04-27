package com.evist0.common.simulation.middlewares;

public interface AsyncSimulationMiddleware extends Runnable {
    @Override
    void run();
}
