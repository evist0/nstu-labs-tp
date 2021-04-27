package com.evist0.common.simulation;

import com.evist0.client.models.AppModel;
import com.evist0.common.simulation.middlewares.SimulationMiddleware;

import java.util.ArrayList;
import java.util.concurrent.*;

public class Simulation {
    private final ScheduledExecutorService _executor = Executors.newSingleThreadScheduledExecutor();
    private final ArrayList<SimulationMiddleware> _middlewares = new ArrayList<>();
    private ScheduledFuture<?> scheduledFuture;

    private boolean _running = false;

    private final AppModel _model;

    public Simulation(AppModel model) {
        _model = model;
    }

    public void start() {
        if (scheduledFuture != null && !scheduledFuture.isCancelled()) {
            stop();
        }

        _model.setStarted(true);

        scheduledFuture = _executor.scheduleAtFixedRate(new Runnable() {
            private final long startedTime = System.nanoTime();
            private long previousTime = startedTime;

            @Override
            public void run() {
                synchronized (_model) {
                    if (_running) {
                        float deltaTime = (System.nanoTime() - previousTime) / 1e9f;

                        for (var middleware : _middlewares) {
                            middleware.onTick(_model, deltaTime);
                        }

                        previousTime = System.nanoTime();
                        _model.setTimePassed((previousTime - startedTime) / 1_000_000_000);
                    }
                }
            }
        }, 0, 20, TimeUnit.MILLISECONDS);

        _running = true;
    }

    public void resume() {
        _running = true;
    }

    public void pause() {
        _running = false;
    }

    public void stop() {
        _model.setStarted(false);
        scheduledFuture.cancel(false);
        _running = false;
    }

    public Simulation use(SimulationMiddleware middleware) {
        _middlewares.add(middleware);

        return this;
    }

    public boolean running() {
        return _running;
    }
}
