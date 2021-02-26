package com.evist0.controllers;

import com.evist0.models.Model;
import com.evist0.tax.Factory;
import com.evist0.views.View;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {
    private final Model _m;
    private final View _v;

    private Timer _t;
    private Clip _clip;

    private String getButtonText(boolean value) {
        return value ? "Стоп" : "Старт";
    }

    private void toggleInputEnabled(boolean enabled) {
        var N1_Input = _v.get_settingsPane().get_N1Input();
        var N2_Input = _v.get_settingsPane().get_N2Input();
        var P1_Input = _v.get_settingsPane().get_P1Input();
        var P2_Input = _v.get_settingsPane().get_P2Input();

        N1_Input.setEnabled(enabled);
        N2_Input.setEnabled(enabled);
        P1_Input.setEnabled(enabled);
        P2_Input.setEnabled(enabled);
    }

    private void initializeDefaultValues() {
        var settingsPane = _v.get_settingsPane();

        settingsPane.get_N1Input().setValue(Integer.toString(_m.get_N1()));
        settingsPane.get_N2Input().setValue(Integer.toString(_m.get_N2()));

        settingsPane.get_P1Input().setValue(Float.toString(_m.get_P1()));
        settingsPane.get_P2Input().setValue(Float.toString(_m.get_P2()));

        settingsPane.get_startButton().setText(getButtonText(_m.get_started()));
    }

    private void setActionListeners() {

        var startButton = _v.get_settingsPane().get_startButton();
        startButton.addActionListener(event -> {
            var inputValid = validateAndUpdateModel();

            if (inputValid) {
                var started = !_m.get_started();
                _m.set_started(started);

                startButton.setText(getButtonText(started));

                if (started) {
                    start();
                } else {
                    stop();
                }

                toggleInputEnabled(!started);
            }
        });
    }

    public Controller(Model model, View view) {
        _m = model;
        _v = view;

        var musicFile = new File("assets/music.wav");
        try {
            var ais = AudioSystem.getAudioInputStream(musicFile);

            _clip = AudioSystem.getClip();
            _clip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }

        initializeDefaultValues();
        setActionListeners();
    }

    private boolean validateAndUpdateModel() {
        var N1_Input = _v.get_settingsPane().get_N1Input();
        var N2_Input = _v.get_settingsPane().get_N2Input();
        var P1_Input = _v.get_settingsPane().get_P1Input();
        var P2_Input = _v.get_settingsPane().get_P2Input();

        var valid = true;

        try {
            var N1 = Integer.parseInt(N1_Input.getValue());
            _m.set_N1(N1);
        } catch (NumberFormatException e) {
            showInputError("N1");
            valid = false;
        }

        try {
            var N2 = Integer.parseInt(N2_Input.getValue());
            _m.set_N2(N2);
        } catch (NumberFormatException e) {
            showInputError("N2");
            valid = false;
        }

        try {
            var P1 = Float.parseFloat(P1_Input.getValue());
            _m.set_P1(P1);
        } catch (NumberFormatException e) {
            showInputError("P1");
            valid = false;
        }

        try {
            var P2 = Float.parseFloat(P2_Input.getValue());
            _m.set_P2(P2);
        } catch (NumberFormatException e) {
            showInputError("P2");
            valid = false;
        }

        return valid;
    }

    private void start() {
        var factory = new Factory(_m);

        final int[] secondsPassed = {0};
        var timer = new Timer();

        timer.schedule((new TimerTask() {
            @Override
            public void run() {
                secondsPassed[0] += 1;

                if (secondsPassed[0] % _m.get_N1() == 0) {
                    var taxpayer = factory.produceIndividual();

                    if (taxpayer != null) {
                        _v.drawTaxpayer(taxpayer);
                    }
                }

                if (secondsPassed[0] % _m.get_N2() == 0) {
                    var taxpayer = factory.produceCompany();

                    if (taxpayer != null) {
                        _v.drawTaxpayer(taxpayer);
                    }
                }
            }
        }), 1000, 1000);
        _t = timer;

        _clip.setFramePosition(0);
        _clip.loop(Clip.LOOP_CONTINUOUSLY);
        _clip.start();
    }

    private void stop() {
        _t.cancel();
        _clip.stop();
        _v.clearTaxpayers();
    }

    private void showInputError(String fieldName) {
        var message = "%s задан неверно".formatted(fieldName);

        _v.showDialog(message);
    }
}
