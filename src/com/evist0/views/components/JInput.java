package com.evist0.views.components;

import javax.swing.*;
import java.awt.*;

public class JInput extends JPanel {

    private final JLabel _label;
    private final JTextField _textField;

    public JInput(String labelText) {
        super();

        setLayout(new BorderLayout());
        this._label = new JLabel(labelText);
        this._textField = new JTextField();

        add(_label, BorderLayout.WEST);
        add(_textField, BorderLayout.CENTER);
    }

    public String getValue() {
        return _textField.getText();
    }

    public void setValue(String newValue) {
        _textField.setText(newValue);
    }

    public String getLabel() {
        return _label.getText();
    }

    public void setLabel(String newLabel) {
        _label.setText(newLabel);
    }

    public void setEnabled(boolean enabled) {
        _textField.setEnabled(enabled);
    }
}
