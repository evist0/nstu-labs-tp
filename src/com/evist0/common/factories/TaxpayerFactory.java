package com.evist0.common.factories;

import com.evist0.client.models.AppModel;
import com.evist0.common.dto.taxpayers.TaxpayerDTOBuilder;
import com.evist0.common.entities.CompanyTaxpayer;
import com.evist0.common.entities.IndividualTaxpayer;
import com.evist0.common.moveSystem.Vector2d;

import java.awt.*;

public class TaxpayerFactory {
    public IndividualTaxpayer produceIndividual(AppModel model) {
        var timestamp = model.getTimePassed();
        var ttl = model.getIndividualTtl();

        var availableArea = model.getAvailableArea();

        var position = getRandomPoint(availableArea);
        var image = IndividualTaxpayer.getRandomImage();

        var destination = getRandomPoint(new Rectangle(
                (int) (availableArea.getWidth() / 2),
                (int) (availableArea.getHeight() / 2),
                (int) (availableArea.getWidth() / 2 - image.getWidth()),
                (int) (availableArea.getHeight() / 2 - image.getHeight())
        ));
        var speed = 200f;

        var dto = new TaxpayerDTOBuilder()
                .setTimestamp(timestamp)
                .setTtl(ttl)
                .setPosition(position)
                .setImage(image)
                .setMoveTo(destination, speed)
                .build();

        return new IndividualTaxpayer(dto);
    }

    public CompanyTaxpayer produceCompany(AppModel model) {
        var timestamp = model.getTimePassed();
        var ttl = model.getCompanyTtl();

        var availableArea = model.getAvailableArea();

        var position = getRandomPoint(availableArea);
        var image = CompanyTaxpayer.getRandomImage();

        var destination = getRandomPoint(new Rectangle(0, 0,
                availableArea.width / 2 - image.getWidth(),
                availableArea.height / 2 - image.getHeight()));
        var speed = 200f;

        var dto = new TaxpayerDTOBuilder()
                .setTimestamp(timestamp)
                .setTtl(ttl)
                .setPosition(position)
                .setImage(image)
                .setMoveTo(destination, speed)
                .build();

        return new CompanyTaxpayer(dto);
    }

    private Vector2d getRandomPoint(Rectangle rectangle) {
        float x = (float) (Math.random() * rectangle.getWidth()) + rectangle.x;
        float y = (float) (Math.random() * rectangle.getHeight()) + rectangle.y;

        return new Vector2d(x, y);
    }
}
