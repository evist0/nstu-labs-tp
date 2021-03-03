package com.evist0.components;

import com.evist0.application.AppController;
import com.evist0.application.AppModel;
import com.evist0.dto.settings.SettingsDTO;
import com.evist0.dto.settings.SettingsDTOBuilder;
import com.evist0.properties.ModelChangeListener;
import com.evist0.properties.ModelChangedEvent;

import javax.swing.*;
import java.awt.*;

public class SettingsPane extends JPanel {
    private final AppController _controller;

    public final JTextField N1 = new JTextField("0");
    public final JTextField N2 = new JTextField("0");

    public final ProbabilityComboBox P1 = new ProbabilityComboBox();
    public final ProbabilityComboBox P2 = new ProbabilityComboBox();

    public final JButton startButton = new JButton("Старт");

    public SettingsPane(AppController controller, AppModel model) {
        super();
        _controller = controller;

        var inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        inputPanel.add(new JLabel("Время генерации:"));
        inputPanel.add(withLabel("Физ. лиц", N1));
        inputPanel.add(withLabel("Юр. лиц", N2));

        inputPanel.add(new JLabel("Вероятность генерации:"));
        inputPanel.add(withLabel("Физ. лиц", P1));
        inputPanel.add(withLabel("Юр. лиц", P2));

        setLayout(new BorderLayout());

        add(inputPanel, BorderLayout.NORTH);
        add(startButton, BorderLayout.SOUTH);

        _initListeners(model);
    }

    public SettingsDTO getSettingsDTO() {
        SettingsDTO dto = null;

        try {
            dto = new SettingsDTOBuilder()
                    .setN1(N1.getText())
                    .setN2(N2.getText())
                    .setP1(P1.getValue())
                    .setP2(P2.getValue())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dto;
    }

    private JPanel withLabel(String labelText, Component component) {
        JLabel label = new JLabel(labelText);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(label, BorderLayout.WEST);
        panel.add(component, BorderLayout.CENTER);

        return panel;
    }

    private void _initListeners(AppModel model) {
        startButton.addActionListener(e -> {
            var dto = getSettingsDTO();
            _controller.toggleSimulation(dto);
        });

        model.addModelChangedListener(new ModelChangeListener() {
            @Override
            public <T> void modelChange(ModelChangedEvent<T> evt) {
                switch (evt.getProperty()) {
                    case N1 -> N1.setText(Integer.toString((int) evt.getValue()));
                    case N2 -> N2.setText(Integer.toString((int) evt.getValue()));
                    case P1 -> P1.setSelectedItem((float) evt.getValue());
                    case P2 -> P2.setSelectedItem((float) evt.getValue());
                    case Started -> onStarted((boolean) evt.getValue());
                }
            }
        });
    }

    private void onStarted(boolean started) {
        N1.setEnabled(!started);
        N2.setEnabled(!started);
        P1.setEnabled(!started);
        P2.setEnabled(!started);

        startButton.setText(started ? "Стоп" : "Старт");
    }
}
