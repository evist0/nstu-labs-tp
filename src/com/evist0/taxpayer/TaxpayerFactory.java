package com.evist0.taxpayer;

import com.evist0.application.AppModel;
import com.evist0.application.AppView;

import java.awt.*;
import java.util.Random;

public class TaxpayerFactory {
    private final Random _r;
    private final AppModel _m;

    private final Rectangle availableArea;

    public TaxpayerFactory(AppModel model, AppView view) {
        _r = new Random();

        _m = model;
        availableArea = view.getBounds();
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
        int x = (int) (Math.random() * (availableArea.getWidth() - 99));
        int y = (int) (Math.random() * (availableArea.getHeight() - 99));
        return new Point(x, y);
    }
}
