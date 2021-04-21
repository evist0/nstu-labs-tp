package com.evist0.dto.taxpayers;

import com.evist0.taxpayer.moveSystem.Vector2d;

import java.awt.image.BufferedImage;

public class TaxpayerDTO {
    public final Long timestamp;
    public final Long ttl;

    public final Vector2d position;
    public final BufferedImage image;

    public final Vector2d destination;
    public final float speed;

    public TaxpayerDTO(Long timestamp, Long ttl, Vector2d position, BufferedImage image, Vector2d destination, float speed) {
        this.timestamp = timestamp;
        this.ttl = ttl;

        this.position = position;
        this.image = image;

        this.destination = destination;
        this.speed = speed;
    }
}
