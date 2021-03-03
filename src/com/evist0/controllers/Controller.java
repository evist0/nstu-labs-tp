package com.evist0.controllers;

import com.evist0.models.Model;
import com.evist0.tax.Habitat;
import com.evist0.tax.HabitatTask;
import com.evist0.views.View;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

public class Controller implements KeyListener {
    private final Habitat _habitat;
    private Clip _clip;

    public Controller(Model model, View view) {
        view.addKeyListener(this);

        _habitat = new Habitat(model, view);

        var musicFile = new File("assets/music.wav");
        try {
            var ais = AudioSystem.getAudioInputStream(musicFile);

            _clip = AudioSystem.getClip();
            _clip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void start() {
        if (!_habitat.started()) {
            _habitat.start();

            _clip.setFramePosition(0);
            _clip.loop(Clip.LOOP_CONTINUOUSLY);
            _clip.start();
        }
    }

    private void stop() {
        if (_habitat.started()) {
            _clip.stop();
            _habitat.stop();
        }
    }

    private void toggleTimerVisibility() {
        _habitat.toggleTimerVisibility();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'b' -> start();
            case 'e' -> stop();
            case 't' -> toggleTimerVisibility();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
