package com.evist0.views;

import com.evist0.tax.ITaxpayer;
import com.evist0.views.panels.CanvasPane;
import com.evist0.views.panels.SettingsPane;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class View extends JFrame {
    private final CanvasPane _canvasPane = new CanvasPane();
    private final SettingsPane _settingsPane = new SettingsPane();
    private final JLabel _timerLabel = new JLabel();

    public View() {
        super("Взлом жёппы");
        setBounds(0, 0, 1280, 720);

        _timerLabel.setFont(new Font("Calibri", Font.BOLD, 20));

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

    public void addTimerToCanvas() {
        _timerLabel.setText("00:00:00");

        var preferredSize = _timerLabel.getPreferredSize();

        _timerLabel.setBounds(0, 0, preferredSize.width, preferredSize.height);
        _canvasPane.addTimer(_timerLabel);
    }

    public void updateTimer(int newSeconds) {
        var hours = newSeconds / 3600;
        var minutes = (newSeconds % 3600) / 60;
        var seconds = newSeconds % 60;

        var timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);

        _timerLabel.setText(timeString);
        var preferredSize = _timerLabel.getPreferredSize();

        _timerLabel.setBounds(0, 0, preferredSize.width, preferredSize.height);
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

    public void clearCanvas() {
        _canvasPane.removeAll();
        _canvasPane.repaint();
    }
}
