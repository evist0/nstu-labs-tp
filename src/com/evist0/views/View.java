package com.evist0.views;

import com.evist0.tax.ITaxpayer;
import com.evist0.views.panels.CanvasPane;
import com.evist0.views.panels.SettingsPane;

import javax.swing.*;
import java.util.Random;

public class View extends JFrame {
    private final CanvasPane _canvasPane = new CanvasPane();
    private final SettingsPane _settingsPane = new SettingsPane();

    public View() {
        super("Взлом жёппы");
        setBounds(0, 0, 1280, 720);

        var splitPane = new JSplitPane();
        splitPane.setDividerLocation(1280 - 300);
        setContentPane(splitPane);

        splitPane.setLeftComponent(_canvasPane);
        splitPane.setRightComponent(_settingsPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public SettingsPane get_settingsPane() {
        return _settingsPane;
    }

    public void showDialog(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public void drawTaxpayer(ITaxpayer taxpayer) {
        var image = taxpayer.get_image();
        var availableArea = _canvasPane.getBounds();

        var random = new Random();

        var x = random.nextInt(availableArea.width);
        var y = random.nextInt(availableArea.height);

        var w = image.getIcon().getIconWidth();
        var h = image.getIcon().getIconHeight();

        image.setBounds(x, y, w, h);

        _canvasPane.add(image);
        _canvasPane.repaint();
    }

    public void clearTaxpayers() {
        _canvasPane.removeAll();
        _canvasPane.repaint();
    }
}
