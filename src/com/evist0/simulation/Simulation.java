package com.evist0.simulation;

import com.evist0.application.AppModel;
import com.evist0.simulation.middlewares.SimulationMiddleware;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Simulation {
    private Timer _timer;
    private final ArrayList<SimulationMiddleware> _middlewares = new ArrayList<>();

    public final AppModel _model;

    public Simulation(AppModel model) {
        _model = model;
    }

    public void start() {
        _model.setStarted(true);

        _timer = new Timer();
        _timer.schedule(new TimerTask() {
            @Override
            public void run() {
                var timePassed = _model.getTimePassed();

                _middlewares.forEach(middleware -> middleware.onTick(_model));

                _model.setTimePassed(timePassed + 1);
            }
        }, 1000, 1000);
    }

    public void stop() {
        _timer.purge();
        _timer.cancel();

        _model.setStarted(false);
    }

    public Simulation use(SimulationMiddleware middleware) {
        _middlewares.add(middleware);

        return this;
    }
}
