package com.evist0.views;

import com.evist0.models.ResultModel;
import com.evist0.tax.entity.AbstractTaxpayer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class View extends JFrame {
    private final JLabel _timerLabel = new JLabel();

    private final Panel _panel = new Panel();

    public View() {
        super("Взлом жёппы");
        setBounds(0, 0, 1280, 720);

        _timerLabel.setFont(new Font("Calibri", Font.BOLD, 20));

        add(_timerLabel);
        add(_panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void taxpayersToPaint(ArrayList<AbstractTaxpayer> taxpayers) {
        _panel.setTaxpayers(taxpayers);
        _panel.repaint();
    }

    public void toggleTimerVisibility() {
        var timerVisible = _timerLabel.isVisible();

        _timerLabel.setVisible(!timerVisible);
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

    public void showResultWindow(ResultModel result) {
        JDialog dialog = new JDialog(this, "...", true);
        JPanel panel = new JPanel(new GridLayout(5, 1));

        JLabel messageLabel = new JLabel("Симуляция закончена.");
        messageLabel.setFont(new Font("Comic Sans", Font.BOLD, 18));

        JLabel individualLabel = new JLabel("Физических лиц: %d".formatted(result.get_individualCounter()));
        individualLabel.setFont(new Font("Times", Font.ITALIC, 14));
        individualLabel.setForeground(new Color(0xff0000));


        JLabel companyLabel = new JLabel("Юрилических лиц: %d".formatted(result.get_companyCounter()));
        companyLabel.setFont(new Font("Times", Font.ITALIC, 14));
        companyLabel.setForeground(new Color(0x00ff00));

        JLabel timeLabel = new JLabel("Времени затрачено: %d".formatted(result.get_timePassed()));
        timeLabel.setFont(new Font("Times", Font.ITALIC, 14));
        timeLabel.setForeground(new Color(0x0000ff));

        panel.add(messageLabel);
        panel.add(individualLabel);
        panel.add(companyLabel);
        panel.add(timeLabel);
        dialog.add(panel);

        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        dialog.setPreferredSize(new Dimension(500, 500));
        dialog.setResizable(false);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
}
