package com.evist0.application;

import com.evist0.components.objects.ObjectsDialog;
import com.evist0.dto.settings.SettingsDTO;
import com.evist0.simulation.Simulation;
import com.evist0.simulation.middlewares.DestroyMiddleware;
import com.evist0.simulation.middlewares.FactoryMiddleware;
import com.evist0.simulation.middlewares.MoveMiddleware;
import com.evist0.taxpayer.TaxpayerFactory;

import java.awt.*;

public class AppController {
    private final AppModel _model;
    private Simulation _simulation;

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
            _model.setIndividualTtl(dto.individualTtl);
            _model.setCompanyTtl(dto.companyTtl);

            _model.setTimePassed(0L);
            _model.setIndividualGenerated(0);
            _model.setCompanyGenerated(0);
            _model.resetTaxpayers();

            _simulation = new Simulation(_model)
                    .use(new DestroyMiddleware())
                    .use(new FactoryMiddleware(new TaxpayerFactory()))
                    .use(new MoveMiddleware());

            _simulation.start();
        }
    }

    public void resumeSimulation() {
        _simulation.start();
    }

    public void stopSimulation() {
        _simulation.stop();
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

    public void setIndividualMove(boolean move) {
        _model.setIndividualMove(move);
    }

    public void setCompanyMove(boolean move) {
        _model.setCompanyMove(move);
    }

    public void updateAvailableArea(Rectangle availableArea) {
        _model.setAvailableArea(availableArea);
    }

    public void showObjectsDialog() {
        new ObjectsDialog(_model.getTaxpayerToTimestamp());
    }
}
