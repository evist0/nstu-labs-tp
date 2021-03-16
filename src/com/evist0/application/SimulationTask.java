package com.evist0.application;

import com.evist0.taxpayer.TaxpayerFactory;

import java.util.TimerTask;

public class SimulationTask extends TimerTask {
    private final AppModel _model;
    private final TaxpayerFactory _factory;

    SimulationTask(AppModel model) {
        _model = model;
        _factory = new TaxpayerFactory(_model);
    }

    @Override
    public void run() {
        var timePassed = _model.getTimePassed();

        if (timePassed % _model.getN1() == 0) {
            var taxpayer = _factory.produceIndividual();

            if (taxpayer != null) {
                var generated = _model.getIndividualGenerated();

                _model.setIndividualGenerated(generated + 1);
                _model.addTaxpayer(taxpayer);
            }
        }

        if (timePassed % _model.getN2() == 0) {
            var taxpayer = _factory.produceCompany();

            if (taxpayer != null) {
                var generated = _model.getCompanyGenerated();

                _model.setCompanyGenerated(generated + 1);
                _model.addTaxpayer(taxpayer);
            }
        }

        _model.setTimePassed(timePassed + 1);
    }
}
