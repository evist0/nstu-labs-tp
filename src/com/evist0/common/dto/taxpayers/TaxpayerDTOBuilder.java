package com.evist0.common.dto.taxpayers;

import com.evist0.common.moveSystem.MoveTask;
import com.evist0.common.moveSystem.Vector2d;

import java.awt.image.BufferedImage;

public class TaxpayerDTOBuilder {
    private Vector2d _position;
    private int _imageVariant;

    private Long _timestamp;
    private Long _ttl;

    private Vector2d _destination;
    private float _speed;

    private MoveTask _moveTask;

    public TaxpayerDTOBuilder() {
    }

    public TaxpayerDTOBuilder setPosition(Vector2d position) {
        _position = position;

        return this;
    }

    public TaxpayerDTOBuilder setImageVariant(int imageVariant) {
        _imageVariant = imageVariant;
        return this;
    }

    public TaxpayerDTOBuilder setTimestamp(Long timestamp) {
        _timestamp = timestamp;

        return this;
    }

    public TaxpayerDTOBuilder setTtl(Long ttl) {
        _ttl = ttl;

        return this;
    }

    public TaxpayerDTOBuilder setMoveTo(Vector2d destination, float speed) {
        _destination = destination;
        _speed = speed;
        return this;
    }

    public TaxpayerDTOBuilder setMoveTask(MoveTask moveTask) {
        _moveTask = moveTask;
        return this;
    }

    public TaxpayerDTO build() {
        return new TaxpayerDTO(_timestamp, _ttl, _position, _destination, _speed, _imageVariant, _moveTask);
    }
}
