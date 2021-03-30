package com.evist0.taxpayer;

import com.evist0.dto.taxpayers.TaxpayerDTO;

import java.awt.*;
import java.util.UUID;

public abstract class AbstractTaxpayer {
    private final UUID _id;

    private final Long _timestamp;
    private final Long _ttl;

    private final Point _position;

    private final Image _image;

    AbstractTaxpayer(TaxpayerDTO dto) {
        _id = UUID.randomUUID();

        _timestamp = dto.timestamp;
        _ttl = dto.ttl;

        _position = dto.position;
        _image = dto.image;
    }

    public void draw(Graphics g) {
        g.drawImage(_image, _position.x, _position.y, null);
    }

    public UUID getId() {
        return _id;
    }

    public Long getTimestamp() {
        return _timestamp;
    }

    public Long getTtl() {
        return _ttl;
    }

    public Point getPosition() {
        return _position;
    }

    public Image getImage() {
        return _image;
    }
}
