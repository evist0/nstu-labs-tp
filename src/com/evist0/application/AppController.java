package com.evist0.application;

import com.evist0.dto.settings.SettingsDTO;

import java.awt.*;
import java.util.Timer;

public class AppController {
    private final AppModel _model;
    private Timer _timer;

    public AppController(AppModel model) {
        _model = model;
    }

    public void startSimulation(SettingsDTO dto) {
        var started = _model.getStarted();

        if (!started) {
            _model.setN1(dto.N1);
            _model.setN2(dto.N2);
            _model.setP1(dto.P1);
            _model.setP2(dto.P2);

            _model.setTimePassed(0L);
            _model.setIndividualGenerated(0);
            _model.setCompanyGenerated(0);
            _model.resetTaxpayers();

            _model.setStarted(true);

            _timer = new Timer();
            _timer.schedule(new SimulationTask(_model), 1000, 1000);
        }
    }

    public void resumeSimulation(SettingsDTO dto) {
        _model.setStarted(true);

        _timer = new Timer();
        _timer.schedule(new SimulationTask(_model), 1000, 1000);
    }

    public void stopSimulation() {
        _timer.purge();
        _timer.cancel();

        _model.setStarted(false);
    }

    public void toggleSimulation(SettingsDTO dto) {
        var started = _model.getStarted();

        if (!started) {
            startSimulation(dto);
        } else {
            stopSimulation();
        }
    }

    public void toggleTimerVisible() {
        var visible = _model.getTimerVisible();
        _model.setTimerVisible(!visible);
    }

    public void setTimerVisible(boolean visible) {
        _model.setTimerVisible(visible);
    }

    public void setDialogVisible(boolean visible) {
        _model.setDialogVisible(visible);
    }

    public void updateAvailableArea(Rectangle availableArea) {
        _model.setAvailableArea(availableArea);
    }
}
