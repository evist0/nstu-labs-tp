package com.evist0.views;

import com.evist0.tax.entity.AbstractTaxpayer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Panel extends JPanel {
    private ArrayList<AbstractTaxpayer> _taxpayers = new ArrayList<>();

    public Panel() {
        super();
    }

    public void setTaxpayers(ArrayList<AbstractTaxpayer> taxpayers) {
        _taxpayers = taxpayers;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (AbstractTaxpayer taxpayer : _taxpayers) {
            taxpayer.draw(g);
        }
    }
}
