package com.evist0.dto.taxpayers;

import java.awt.*;

public class TaxpayerDTO {
    public final Long timestamp;
    public final Long ttl;

    public final Point position;
    public final Image image;

    public TaxpayerDTO(Long timestamp, Long ttl, Point position, Image image) {
        this.timestamp = timestamp;
        this.ttl = ttl;

        this.position = position;
        this.image = image;
    }
}
