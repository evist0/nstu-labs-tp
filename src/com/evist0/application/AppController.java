package com.evist0.application;

import com.evist0.dto.settings.SettingsDTO;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class AppController {
    private final AppModel _model;
    private Clip _clip;

    public AppController(AppModel model) {
        _model = model;

        var musicFile = new File("assets/music.wav");
        try {
            var ais = AudioSystem.getAudioInputStream(musicFile);

            _clip = AudioSystem.getClip();
            _clip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start(SettingsDTO dto) {
        _model.setN1(dto.N1);
        _model.setN2(dto.N2);
        _model.setP1(dto.P1);
        _model.setP2(dto.P2);

        _clip.setFramePosition(0);
        _clip.loop(Clip.LOOP_CONTINUOUSLY);

        _model.setStarted(true);
    }

    public void stop() {
        _clip.stop();

        _model.setStarted(false);
    }

    public void toggleSimulation(SettingsDTO dto) {
        var started = _model.getStarted();

        if (!started) {
            start(dto);
        } else {
            stop();
        }
    }

    public void toggleTimer() {
    }
}
