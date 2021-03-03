package com.evist0.application;

import com.evist0.components.Menubar;
import com.evist0.dto.settings.SettingsDTO;
import com.evist0.dto.settings.SettingsDTOBuilder;
import com.evist0.properties.ModelChangeListener;
import com.evist0.properties.ModelChangedEvent;
import com.evist0.taxpayer.AbstractTaxpayer;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

public class AppView extends JFrame {
    private Clip clip;

    private JPanel content;
    private JPanel canvasPanel;
    private JPanel settingsPanel;

    private JTextField N1;
    private JTextField N2;

    private final ArrayList<ProbabilityModel> _probabilities = new ArrayList<>();
    private JComboBox<ProbabilityModel> P1;
    private JComboBox<ProbabilityModel> P2;

    private JButton startButton;
    private JLabel timerLabel;

    public SettingsDTO getSettingsDTO() {
        var builder = new SettingsDTOBuilder()
                .setN1(N1.getText())
                .setN2(N2.getText())
                .setP1(_getProbabilityFromModel(P1.getSelectedItem()))
                .setP2(_getProbabilityFromModel(P2.getSelectedItem()));

        return builder.build();
    }

    public AppView(AppController controller, AppModel model) {
        super("Взлом жопы");
        setBounds(0, 0, 1280, 720);
        setFocusableWindowState(true);

        var menubar = new Menubar(controller, this);
        setJMenuBar(menubar);

        add(content);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        var musicFile = new File("assets/music.wav");
        try {
            var ais = AudioSystem.getAudioInputStream(musicFile);

            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }

        _initComboBoxes();
        _initActionListeners(controller);
        _initModelChangeListeners(model);
    }

    private void _initComboBoxes() {
        for (int i = 0; i <= 100; i += 10) {
            var string = i + "%";
            var value = (float) i / 100;

            var probability = new ProbabilityModel(value, string);

            _probabilities.add(probability);
            P1.addItem(probability);
            P2.addItem(probability);
        }
    }

    private void _initActionListeners(AppController controller) {
        var blurListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                requestFocus();
            }
        };

        var keyTypedListener = new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                switch (e.getKeyChar()) {
                    case 'b' -> {
                        var dto = getSettingsDTO();
                        controller.start(dto);
                    }
                    case 't' -> controller.toggleTimer();
                    case 'e' -> controller.stop();
                }
            }
        };

        var resizeListener = new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                controller.updateAvailableArea(e.getComponent().getBounds());
            }
        };

        addKeyListener(keyTypedListener);

        canvasPanel.addMouseListener(blurListener);
        settingsPanel.addMouseListener(blurListener);

        canvasPanel.addComponentListener(resizeListener);

        startButton.addActionListener(e -> {
            var dto = getSettingsDTO();
            controller.toggleSimulation(dto);
        });
    }

    private void _initModelChangeListeners(AppModel model) {
        var modelChangeListener = new ModelChangeListener() {
            @Override
            public <T> void modelChange(ModelChangedEvent<T> evt) {
                switch (evt.getProperty()) {
                    case N1 -> N1.setText(Integer.toString((int) evt.getValue()));
                    case N2 -> N2.setText(Integer.toString((int) evt.getValue()));
                    case P1 -> {
                        var item = _findProbabilityItem((float) evt.getValue());
                        P1.setSelectedItem(item);
                    }
                    case P2 -> {
                        var item = _findProbabilityItem((float) evt.getValue());
                        P2.setSelectedItem(item);
                    }
                    case Started -> onStartedToggle((boolean) evt.getValue());
                    case TimePassed -> onSecondsPassed((Long) evt.getValue());
                    case TimerVisibility -> timerLabel.setVisible((boolean) evt.getValue());
                    case Taxpayers -> drawTaxpayers((ArrayList<AbstractTaxpayer>) evt.getValue());
                }
            }
        };

        model.addModelChangedListener(modelChangeListener);
    }

    private void onStartedToggle(boolean started) {
        N1.setEnabled(!started);
        N2.setEnabled(!started);
        P1.setEnabled(!started);
        P2.setEnabled(!started);

        startButton.setText(started ? "Стоп" : "Старт");

        if (started) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            clip.stop();
        }
    }

    private void onSecondsPassed(Long secondsPassed) {
        var hours = secondsPassed / 3600;
        var minutes = (secondsPassed % 3600) / 60;
        var seconds = secondsPassed % 60;

        var timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);

        timerLabel.setText(timeString);
    }

    private void drawTaxpayers(ArrayList<AbstractTaxpayer> taxpayers) {
        var g = canvasPanel.getGraphics();
        var bounds = canvasPanel.getBounds();

        g.clearRect(bounds.x, bounds.y, bounds.width, bounds.height);

        for (AbstractTaxpayer taxpayer : taxpayers) {
            taxpayer.draw(g);
        }
    }

    private ProbabilityModel _findProbabilityItem(float value) {
        for (var probability : _probabilities) {
            if (probability.getValue() == value) {
                return probability;
            }
        }

        return null;
    }

    private float _getProbabilityFromModel(Object model) {
        return ((ProbabilityModel) model).getValue();
    }
}
