package com.evist0.common.dto.taxpayers;

import com.evist0.common.moveSystem.Vector2d;

import java.awt.image.BufferedImage;

public class TaxpayerDTOBuilder {
    private Vector2d _position;
    private BufferedImage _image;

    private Long _timestamp;
    private Long _ttl;

    private Vector2d _destination;
    private float _speed;

    public TaxpayerDTOBuilder() {
    }

    public TaxpayerDTOBuilder setPosition(Vector2d position) {
        _position = position;

        return this;
    }

    public TaxpayerDTOBuilder setImage(BufferedImage image) {
        _image = image;

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

    public TaxpayerDTO build() {
        return new TaxpayerDTO(_timestamp, _ttl, _position, _image, _destination, _speed);
    }
}
