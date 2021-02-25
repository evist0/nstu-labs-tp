package com.evist0.controllers;

import com.evist0.tax.Factory;
import com.evist0.models.Model;
import com.evist0.views.View;

import javax.sound.sampled.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {

    private final Model _m;
    private final View _v;

    private Timer _t;

    private Clip _clip;

    private void initializeDefaultValues() {
        var settingsPane = _v.get_settingsPane();

        settingsPane.get_N1Input().setValue(Integer.toString(_m.get_N1()));
        settingsPane.get_N2Input().setValue(Integer.toString(_m.get_N2()));

        settingsPane.get_P1Input().setValue(Float.toString(_m.get_P1()));
        settingsPane.get_P2Input().setValue(Float.toString(_m.get_P2()));

        settingsPane.get_startButton().setText(getButtonText(_m.get_started()));
    }

    private void setActionListeners() {
        var N1_Input = _v.get_settingsPane().get_N1Input();
        N1_Input.addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateModel();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateModel();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateModel();
            }

            private void updateModel() {
                var value = N1_Input.getValue();
                var parsed = parseInt(value);

                if (parsed <= 0) {
                    _v.showDialog("N1 введён неверно");
                    _m.set_N1(1);
                } else {
                    _m.set_N1(parsed);
                }
            }
        });

        var N2_Input = _v.get_settingsPane().get_N2Input();
        N2_Input.addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateModel();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateModel();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateModel();
            }

            private void updateModel() {
                var value = N2_Input.getValue();
                var parsed = parseInt(value);

                if (parsed <= 0) {
                    _v.showDialog("N2 введён неверно");
                    _m.set_N2(1);
                } else {
                    _m.set_N2(parsed);
                }
            }
        });

        var P1_Input = _v.get_settingsPane().get_P1Input();
        P1_Input.addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateModel();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateModel();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateModel();
            }

            private void updateModel() {
                var value = P1_Input.getValue();

                var parsed = parseFloat(value);

                if (parsed < 0 || parsed > 1) {
                    _v.showDialog("P1 введён неверно");
                    _m.set_P1(0);
                } else {
                    _m.set_P1(parsed);
                }
            }
        });

        var P2_Input = _v.get_settingsPane().get_P2Input();
        P2_Input.addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateModel();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateModel();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateModel();
            }

            private void updateModel() {
                var value = P2_Input.getValue();

                var parsed = parseFloat(value);

                if (parsed < 0 || parsed > 1) {
                    _v.showDialog("P2 введён неверно");
                    _m.set_P2(0);
                } else {
                    _m.set_P2(parsed);
                }
            }
        });

        var startButton = _v.get_settingsPane().get_startButton();
        startButton.addActionListener(e -> {
            var newValue = !_m.get_started();

            _m.set_started(newValue);

            startButton.setText(getButtonText(newValue));

            if (newValue) {
                start();
            } else {
                stop();
            }
        });
    }

    private int parseInt(String value) {
        var cpy = value.replaceAll("\\D", "");

        if (cpy.length() == 0) {
            cpy = "0";
        }

        return Integer.parseInt(cpy);
    }

    private float parseFloat(String value) {
        var cpy = value.replaceAll("\\D", "");

        if (cpy.length() == 0) {
            cpy = "0";
        }

        return Float.parseFloat(cpy);
    }

    private String getButtonText(boolean value) {
        return value ? "Стоп" : "Старт";
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

    private void start() {
        final int[] secondsPassed = {0};
        var timer = new Timer();

        var factory = new Factory(_m);

        _clip.setFramePosition(0);
        _clip.loop(Clip.LOOP_CONTINUOUSLY);
        _clip.start();

        timer.schedule((new TimerTask() {
            @Override
            public void run() {
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

                secondsPassed[0] += 1;

            }
        }), 1000, 1000);

        _t = timer;
    }

    private void stop() {
        _clip.stop();
        _t.cancel();
    }
}
