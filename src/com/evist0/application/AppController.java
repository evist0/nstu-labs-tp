package com.evist0.application;

import com.evist0.dto.settings.SettingsDTO;
import com.evist0.taxpayer.TaxpayerFactory;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class AppController {
    private final AppModel _model;
    private final Timer _timer = new Timer();

    public AppController(AppModel model) {
        _model = model;
    }

    public void start(SettingsDTO dto) {
        var started = _model.getStarted();

        if (!started) {
            _model.setN1(dto.N1);
            _model.setN2(dto.N2);
            _model.setP1(dto.P1);
            _model.setP2(dto.P2);
            _model.setTimePassed(0L);

            _model.setStarted(true);

            _timer.schedule(new TimerTask() {
                TaxpayerFactory factory = new TaxpayerFactory(_model);

                @Override
                public void run() {
                    var timePassed = _model.getTimePassed();

                    if (timePassed % _model.getN1() == 0) {
                        var taxpayer = factory.produceIndividual();

                        if (taxpayer != null) {
                            _model.addTaxpayer(taxpayer);
                        }
                    }

                    if (timePassed % _model.getN2() == 0) {
                        var taxpayer = factory.produceCompany();

                        if (taxpayer != null) {
                            _model.addTaxpayer(taxpayer);
                        }
                    }

                    _model.setTimePassed(timePassed + 1);
                }
            }, 1000, 1000);
        }
    }

    public void stop() {
        _model.setStarted(false);
        _timer.cancel();
    }

    public void toggleSimulation(SettingsDTO dto) {
        var started = _model.getStarted();

        if (!started) {
            start(dto);
        } else {
            stop();
        }
    }

    public void toggleTimer() {
        _model.toggleTimer();
    }
    public void setTimerVisible() {
        _model.setTimerVisble();
    }
    public void setTimerInvisible() {
        _model.setTimerInvisible();
    }
    public void toggleDialogVisible(){
        _model.toggleDialogVisible();
    }


    public void updateAvailableArea(Rectangle availableArea) {
        _model.setAvailableArea(availableArea);
    }
}
