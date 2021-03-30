package com.evist0.taxpayer;

import com.evist0.application.AppModel;
import com.evist0.dto.taxpayers.TaxpayerDTOBuilder;

import java.awt.*;

public class TaxpayerFactory {
    public IndividualTaxpayer produceIndividual(AppModel model) {
        var timestamp = model.getTimePassed();
        var ttl = model.getIndividualTtl();

        var position = getRandomPoint(model);
        var image = IndividualTaxpayer.getRandomImage();

        var dto = new TaxpayerDTOBuilder()
                .setTimestamp(timestamp)
                .setTtl(ttl)
                .setPosition(position)
                .setImage(image)
                .build();

        return new IndividualTaxpayer(dto);
    }

    public CompanyTaxpayer produceCompany(AppModel model) {
        var timestamp = model.getTimePassed();
        var ttl = model.getCompanyTtl();

        var position = getRandomPoint(model);
        var image = CompanyTaxpayer.getRandomImage();

        var dto = new TaxpayerDTOBuilder()
                .setTimestamp(timestamp)
                .setTtl(ttl)
                .setPosition(position)
                .setImage(image)
                .build();

        return new CompanyTaxpayer(dto);
    }

    private Point getRandomPoint(AppModel model) {
        int x = (int) (Math.random() * (model.getAvailableArea().getWidth() - 99));
        int y = (int) (Math.random() * (model.getAvailableArea().getHeight() - 99));

        return new Point(x, y);
    }
}
