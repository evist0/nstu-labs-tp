package com.evist0.tax;

import java.util.TimerTask;

public class HabitatTask extends TimerTask {
    private final Habitat _h;

    public HabitatTask(Habitat habitat) {
        _h = habitat;
    }

    @Override
    public void run() {
        _h.update();
    }
}
