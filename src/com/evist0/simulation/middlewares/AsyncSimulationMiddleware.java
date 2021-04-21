package com.evist0.simulation.middlewares;

public interface AsyncSimulationMiddleware extends Runnable {
    @Override
    void run();
}
