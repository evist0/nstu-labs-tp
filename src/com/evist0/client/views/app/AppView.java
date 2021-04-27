package com.evist0.client.views.app;

import com.evist0.client.AppController;
import com.evist0.client.models.AppModel;
import com.evist0.client.models.ProbabilityModel;
import com.evist0.client.views.results.ResultsDialog;
import com.evist0.common.dto.settings.SettingsDTO;
import com.evist0.common.dto.settings.SettingsDTOBuilder;
import com.evist0.common.dto.settings.SettingsException;
import com.evist0.common.dto.settings.SettingsExceptionHandler;
import com.evist0.common.properties.ModelChangeListener;
import com.evist0.common.properties.ModelChangedEvent;
import com.evist0.common.entities.Entity;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

public class AppView extends JFrame {
    private final AppController _controller;
    private final AppModel _model;

    private Clip clip;

    private final Menubar menubar;
    private JPanel content;

    private JLabel timerLabel;
    private JPanel canvasPanel;

    private JPanel settingsPanel;

    private JTextField N1TextField;
    private JTextField N2TextField;

    private final ArrayList<ProbabilityModel> _probabilities = new ArrayList<>();
    private JComboBox<ProbabilityModel> P1ComboBox;
    private JComboBox<ProbabilityModel> P2ComboBox;

    private JCheckBox showResultsCheckBox;

    private JRadioButton showTimerRadioButton;
    private JRadioButton hideTimerRadioButton;

    private JButton startButton;
    private JTextField individualTtlTextField;
    private JTextField companyTtlTextField;

    private JCheckBox individualMoveCheckBox;
    private JCheckBox companyMoveCheckBox;

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    public SettingsDTO getSettingsDTO() throws SettingsException {
        var builder = new SettingsDTOBuilder()
                .setN1(N1TextField.getText())
                .setN2(N2TextField.getText())
                .setP1(_getProbabilityFromModel(P1ComboBox.getSelectedItem()))
                .setP2(_getProbabilityFromModel(P2ComboBox.getSelectedItem()))
                .setIndividualTtl(individualTtlTextField.getText())
                .setCompanyTtl(companyTtlTextField.getText());


        return builder.build();
    }

    public AppView(AppController controller, AppModel model) {
        super("Взлом жопы");
        setBounds(0, 0, 1280, 720);
        setFocusableWindowState(true);

        _model = model;
        _controller = controller;

        canvasPanel.setIgnoreRepaint(true);
        Timer _timer = new Timer(16, e -> {
            synchronized (_model) {
                drawTaxpayers(_model.getTaxpayers());
            }
        });
        _timer.start();

        menubar = new Menubar(controller, this, model);
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
        _initModelChangeListeners();

        _setViewFromModel();
    }

    private void _initComboBoxes() {
        for (int i = 0; i <= 100; i += 10) {
            var string = i + "%";
            var value = (float) i / 100;

            var probability = new ProbabilityModel(value, string);

            _probabilities.add(probability);
            P1ComboBox.addItem(probability);
            P2ComboBox.addItem(probability);
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
                    case 'b' -> onStart();
                    case 't' -> controller.toggleTimerVisible();
                    case 'e' -> controller.stopSimulation();
                }
            }
        };

        var resizeListener = new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                var newBounds = e.getComponent().getBounds();

                controller.updateAvailableArea(newBounds);
            }
        };

        addKeyListener(keyTypedListener);

        canvasPanel.addMouseListener(blurListener);
        settingsPanel.addMouseListener(blurListener);

        canvasPanel.addComponentListener(resizeListener);

        showResultsCheckBox.addActionListener(e -> {
            var checkBox = (JCheckBox) e.getSource();
            var selected = checkBox.isSelected();

            controller.setDialogVisible(selected);
        });

        individualMoveCheckBox.addActionListener(e -> {
            var checkBox = (JCheckBox) e.getSource();
            var selected = checkBox.isSelected();

            controller.setIndividualMove(selected);
        });

        companyMoveCheckBox.addActionListener(e -> {
            var checkBox = (JCheckBox) e.getSource();
            var selected = checkBox.isSelected();

            controller.setCompanyMove(selected);
        });

        showTimerRadioButton.addActionListener(e -> controller.setTimerVisible(true));
        hideTimerRadioButton.addActionListener(e -> controller.setTimerVisible(false));

        startButton.addActionListener(e -> onStart());
    }

    private void _initModelChangeListeners() {
        var modelChangeListener = new ModelChangeListener() {
            @Override
            public <T> void modelChange(ModelChangedEvent<T> evt) {
                switch (evt.getProperty()) {
                    case Started -> onStartToggle((boolean) evt.getValue());
                    case TimePassed -> onSecondsPassed((Long) evt.getValue());
                    case N1 -> N1TextField.setText(Long.toString((Long) evt.getValue()));
                    case N2 -> N2TextField.setText(Long.toString((Long) evt.getValue()));
                    case P1 -> {
                        var item = _findProbabilityItem((float) evt.getValue());
                        P1ComboBox.setSelectedItem(item);
                    }
                    case P2 -> {
                        var item = _findProbabilityItem((float) evt.getValue());
                        P2ComboBox.setSelectedItem(item);
                    }
                    case IndividualTtl -> individualTtlTextField.setText(Long.toString((Long) evt.getValue()));
                    case CompanyTtl -> companyTtlTextField.setText(Long.toString((Long) evt.getValue()));
                    case TimerVisibility -> {
                        timerLabel.setVisible((boolean) evt.getValue());
                        showTimerRadioButton.setSelected((boolean) evt.getValue());
                        hideTimerRadioButton.setSelected(!(boolean) evt.getValue());
                    }
                    case ResultsVisibility -> showResultsCheckBox.setSelected((boolean) evt.getValue());
                    case IndividualMove -> individualMoveCheckBox.setSelected((boolean) evt.getValue());
                    case CompanyMove -> companyMoveCheckBox.setSelected((boolean) evt.getValue());
                }
            }
        };

        _model.addModelChangedListener(modelChangeListener);
    }

    private void onStart() {
        try {
            var dto = getSettingsDTO();
            _controller.toggleSimulation(dto);
        } catch (SettingsException exception) {
            var handler = new SettingsExceptionHandler(_model);
            handler.handleException(exception);

            showErrorMessage(exception.getMessage());
        }
    }

    private void onStartToggle(boolean started) {
        N1TextField.setEnabled(!started);
        N2TextField.setEnabled(!started);
        P1ComboBox.setEnabled(!started);
        P2ComboBox.setEnabled(!started);
        individualTtlTextField.setEnabled(!started);
        companyTtlTextField.setEnabled(!started);

        startButton.setText(started ? "Стоп" : "Старт");

        if (started) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            clip.stop();

            var shouldShowResults = _model.getDialogVisible();

            if (shouldShowResults) {
                new ResultsDialog(_controller, _model);
            }
        }
    }

    private void onSecondsPassed(Long secondsPassed) {
        var hours = secondsPassed / 3600;
        var minutes = (secondsPassed % 3600) / 60;
        var seconds = secondsPassed % 60;

        var timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);

        timerLabel.setText(timeString);
    }

    private void drawTaxpayers(Vector<Entity> taxpayers) {
        var g = canvasPanel.getGraphics();
        var bounds = canvasPanel.getBounds();

        if (bounds.isEmpty())
            return;

        var bufferedImage = new BufferedImage(canvasPanel.getWidth(), canvasPanel.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        var bufferedGraphics = bufferedImage.getGraphics();

        for (Entity taxpayer : taxpayers) {
            taxpayer.draw(bufferedGraphics);
        }

        g.clearRect(bounds.x, bounds.y, bounds.width, bounds.height);
        g.drawImage(bufferedImage, 0, 0, null);

        menubar.repaint();
        menubar.repaintChildren();
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

    private void _setViewFromModel() {
        N1TextField.setText(Long.toString(_model.getN1()));
        N2TextField.setText(Long.toString(_model.getN2()));

        P1ComboBox.setSelectedItem(_findProbabilityItem(_model.getP1()));
        P2ComboBox.setSelectedItem(_findProbabilityItem(_model.getP2()));

        individualTtlTextField.setText(Long.toString(_model.getIndividualTtl()));
        companyTtlTextField.setText(Long.toString(_model.getCompanyTtl()));

        var timerVisible = _model.getTimerVisible();

        timerLabel.setVisible(timerVisible);
        showTimerRadioButton.setSelected(timerVisible);
        hideTimerRadioButton.setSelected(!timerVisible);

        individualMoveCheckBox.setSelected(_model.getIndividualMove());
        companyMoveCheckBox.setSelected(_model.getCompanyMove());

        showResultsCheckBox.setSelected(_model.getDialogVisible());
    }

    @Override
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            try {
                _model.save();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        super.processWindowEvent(e);
    }
}
