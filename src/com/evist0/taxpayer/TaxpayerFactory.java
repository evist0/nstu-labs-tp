package com.evist0.taxpayer;

import com.evist0.application.AppModel;

import java.awt.*;
import java.util.Random;

public class TaxpayerFactory {
    private final Random _r = new Random();
    private final AppModel _m;

    public TaxpayerFactory(AppModel model) {
        _m = model;
    }

    public IndividualTaxpayer produceIndividual() {
        var P1 = _m.getP1();

        var generated = _r.nextDouble();
        var point = getRandomPoint();
        var image = IndividualTaxpayer.getRandomImage();

        if (generated <= P1) {
            return new IndividualTaxpayer(point.x, point.y, image);
        } else {
            return null;
        }
    }

    public CompanyTaxpayer produceCompany() {
        var P2 = _m.getP2();

        var generated = _r.nextDouble();
        var point = getRandomPoint();
        var image = CompanyTaxpayer.getRandomImage();


        if (generated <= P2) {
            return new CompanyTaxpayer(point.x, point.y, image);
        } else {
            return null;
        }
    }

    private Point getRandomPoint() {
        int x = (int) (Math.random() * (_m.getAvailableArea().getWidth() - 99));
        int y = (int) (Math.random() * (_m.getAvailableArea().getHeight() - 99));
        return new Point(x, y);
    }
}
