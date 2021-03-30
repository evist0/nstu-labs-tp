package com.evist0.dto.taxpayers;

import java.awt.*;

public class TaxpayerDTOBuilder {
    private Point _position;
    private Image _image;

    private Long _timestamp;
    private Long _ttl;

    public TaxpayerDTOBuilder() {
    }

    public TaxpayerDTOBuilder setPosition(Point position) {
        _position = position;

        return this;
    }

    public TaxpayerDTOBuilder setImage(Image image) {
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

    public TaxpayerDTO build() {
        return new TaxpayerDTO(_timestamp, _ttl, _position, _image);
    }
}
