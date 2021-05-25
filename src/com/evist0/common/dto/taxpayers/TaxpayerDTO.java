package com.evist0.common.dto.taxpayers;

import com.evist0.common.moveSystem.MoveTask;
import com.evist0.common.moveSystem.Vector2d;

import java.awt.image.BufferedImage;

public class TaxpayerDTO {
    public final Long timestamp;
    public final Long ttl;

    public final Vector2d position;
    public final int imageVariant;

    public final Vector2d destination;
    public final float speed;

    public final MoveTask moveTask;

    public TaxpayerDTO(Long timestamp, Long ttl, Vector2d position, Vector2d destination, float speed, int imageVariant, MoveTask moveTask) {
        this.timestamp = timestamp;
        this.ttl = ttl;

        this.position = position;
        this.imageVariant = imageVariant;

        this.destination = destination;
        this.speed = speed;

        this.moveTask = moveTask;
    }
}
