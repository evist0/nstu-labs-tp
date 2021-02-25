package com.evist0.tax;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class IndividualTaxpayer implements ITaxpayer {
    private final JLabel _image;

    private final static File[] _images = new File[]{
            new File("assets/images/individual_0.jpg"),
            new File("assets/images/individual_1.png"),
            new File("assets/images/individual_2.png"),
            new File("assets/images/individual_3.png"),
    };

    @Override
    public JLabel get_image() {
        return _image;
    }

    public IndividualTaxpayer() {
        JLabel tmp;
        var _r = new Random();
        var imageIdx = _r.nextInt(4);

        System.out.println(imageIdx);

        BufferedImage img;
        try {
            img = ImageIO.read(_images[imageIdx]);
            var icon = new ImageIcon(img);

            tmp = new JLabel(icon);
        } catch (IOException e) {
            e.printStackTrace();
            tmp = new JLabel("404 individual not found");
        }

        _image = tmp;
    }
}
