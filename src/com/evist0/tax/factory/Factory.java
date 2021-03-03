package com.evist0.tax.factory;

import com.evist0.models.Model;
import com.evist0.tax.entity.CompanyTaxpayer;
import com.evist0.tax.entity.IndividualTaxpayer;
import com.evist0.views.View;

import java.awt.*;
import java.util.Random;

public class Factory {
    private final Random _r;
    private final Model _m;

    private final Rectangle availableArea;

    public Factory(Model model, View view) {
        _r = new Random();

        _m = model;
        availableArea = view.getBounds();
    }

    public IndividualTaxpayer produceIndividual() {
        var P1 = _m.get_P1();

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
        var P2 = _m.get_P2();

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
