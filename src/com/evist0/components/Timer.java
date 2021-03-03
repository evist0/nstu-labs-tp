package com.evist0.components;

import javax.swing.*;

public class Timer extends JLabel {
    public Timer() {
        super();
        setText("00:00:00");

        var preferredSize = getPreferredSize();
        setBounds(0, 0, preferredSize.width, preferredSize.height);

        setVisible(true);
    }
}
