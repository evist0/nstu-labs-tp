package com.evist0.common.entities;

import com.evist0.common.dto.taxpayers.TaxpayerDTO;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class CompanyTaxpayer extends Entity {
    public static int counter = 0;

    private final static File[] _images = new File[]{
            new File("assets/images/company_0.png"),
            new File("assets/images/company_1.png"),
            new File("assets/images/company_2.png"),
            new File("assets/images/company_3.png"),
    };

    public CompanyTaxpayer(TaxpayerDTO dto) {
        super(dto);
        counter++;
    }

    public static BufferedImage getImage(int id) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(_images[id]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    public static int getRandomImageId() {
        var _r = new Random();
        return _r.nextInt(4);
    }
}
