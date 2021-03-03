package com.evist0.tax;

import com.evist0.models.Model;
import com.evist0.models.ResultModel;
import com.evist0.tax.entity.AbstractTaxpayer;
import com.evist0.tax.entity.CompanyTaxpayer;
import com.evist0.tax.entity.IndividualTaxpayer;
import com.evist0.tax.factory.Factory;
import com.evist0.views.View;

import java.util.ArrayList;
import java.util.Timer;

public class Habitat {
    private final Model _m;
    private final View _v;

    private Timer _t;
    private Factory _factory;

    private final ArrayList<AbstractTaxpayer> _taxpayers;
    private int time;

    public Habitat(Model m, View v) {
        _m = m;
        _v = v;

        _taxpayers = new ArrayList<>();
    }

    public void start() {
        _factory = new Factory(_m, _v);
        _m.set_started(true);
        _t = new Timer();

        _t.schedule(new HabitatTask(this), 1000, 1000);
    }

    public void update() {
        time++;

        if (time % _m.get_N1() == 0) {
            var taxpayer = _factory.produceIndividual();

            if (taxpayer != null) {
                _taxpayers.add(taxpayer);
            }
        }

        if (time % _m.get_N2() == 0) {
            var taxpayer = _factory.produceCompany();

            if (taxpayer != null) {
                _taxpayers.add(taxpayer);
            }
        }

        _v.taxpayersToPaint(_taxpayers);
        _v.updateTimer(time);
    }

    public void stop() {
        var result = new ResultModel(IndividualTaxpayer.counter, CompanyTaxpayer.counter, time);

        _m.set_started(false);
        _t.cancel();

        _v.showResultWindow(result);
    }

    public boolean started() {
        return _m.get_started();
    }

    public void toggleTimerVisibility() {
        _v.toggleTimerVisibility();
    }
}
