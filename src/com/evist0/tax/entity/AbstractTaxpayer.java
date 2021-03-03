package com.evist0.tax.entity;

import java.awt.*;

public abstract class AbstractTaxpayer implements IBehaviour {
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

    @Override
    public int getX() {
        return _x;
    }

    @Override
    public int getY() {
        return _y;
    }

    @Override
    public void setX(int x) {
        this._x = x;
    }

    @Override
    public void setY(int y) {
        this._y = y;
    }

    @Override
    public Image get_image() {
        return _image;
    }
}
