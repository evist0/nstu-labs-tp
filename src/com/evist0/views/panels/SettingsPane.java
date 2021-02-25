package com.evist0.views.panels;

import com.evist0.views.components.JInput;

import javax.swing.*;
import java.awt.*;

public class SettingsPane extends JPanel {
    private final JInput _N1Input = new JInput("Физических лиц");
    private final JInput _N2Input = new JInput("Юридических лиц");

    private final JInput _P1Input = new JInput("Физических лиц");
    private final JInput _P2Input = new JInput("Юридических лиц");

    private final JButton _startButton = new JButton("Старт");

    private JPanel InitInputsPane() {
        var result = new JPanel();
        var layout = new BoxLayout(result, BoxLayout.Y_AXIS);

        result.setAlignmentX(0);
        result.setLayout(layout);

        result.add(new JLabel("Время генерации"));
        result.add(_N1Input);
        result.add(_N2Input);

        result.add(Box.createRigidArea(new Dimension(0, 30)));

        result.add(new JLabel("Вероятность генерации"));
        result.add(_P1Input);
        result.add(_P2Input);

        return result;
    }

    public SettingsPane() {
        super();
        setLayout(new BorderLayout());
        add(InitInputsPane(), BorderLayout.NORTH);
        add(_startButton, BorderLayout.SOUTH);
    }

    public JInput get_N1Input() {
        return _N1Input;
    }

    public JInput get_N2Input() {
        return _N2Input;
    }

    public JInput get_P1Input() {
        return _P1Input;
    }

    public JInput get_P2Input() {
        return _P2Input;
    }

    public JButton get_startButton() {
        return _startButton;
    }
}
