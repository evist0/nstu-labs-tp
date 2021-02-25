package com.evist0.tax;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class CompanyTaxpayer implements ITaxpayer {
    private final JLabel _image;

    private final static File[] _images = new File[]{
            new File("assets/images/company_0.png"),
            new File("assets/images/company_1.png"),
            new File("assets/images/company_2.png"),
            new File("assets/images/company_3.png"),
    };

    @Override
    public JLabel get_image() {
        return _image;
    }

    public CompanyTaxpayer() {
        JLabel tmp;
        var _r = new Random();
        var imageIdx = _r.nextInt(4);

        BufferedImage img;
        try {
            img = ImageIO.read(_images[imageIdx]);
            var icon = new ImageIcon(img);

            tmp = new JLabel(icon);
        } catch (IOException e) {
            tmp = new JLabel("404 company not found");
        }

        _image = tmp;
    }
}
