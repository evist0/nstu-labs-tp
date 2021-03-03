package com.evist0.taxpayer;

import java.awt.*;

public abstract class AbstractTaxpayer {
    private final Image _image;
    private int _x;
    private int _y;

    AbstractTaxpayer(int x, int y, Image image) {
        _x = x;
        _y = y;

        _image = image;
    }

    public void draw(Graphics g) {
        g.drawImage(_image, _x, _y, null);
    }

    public int getX() {
        return _x;
    }

    public int getY() {
        return _y;
    }

    public void setX(int x) {
        this._x = x;
    }

    public void setY(int y) {
        this._y = y;
    }

    public Image get_image() {
        return _image;
    }
}
