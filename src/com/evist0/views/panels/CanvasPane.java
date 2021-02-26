package com.evist0.views.panels;

import javax.swing.*;
import java.awt.*;

public class CanvasPane extends JLayeredPane {
    private int _layerCounter = 0;

    public CanvasPane() {
        super();
        setLayout(null);
        setBackground(Color.WHITE);
    }

    @Override
    public Component add(Component comp) {
        super.add(comp);
        setLayer(comp, _layerCounter++);

        return comp;
    }

    public void addTimer(Component comp) {
        super.add(comp);
        setLayer(comp, Integer.MAX_VALUE);
        repaint();
    }
}
