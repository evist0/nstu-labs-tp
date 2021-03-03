package com.evist0.components;

import com.evist0.application.AppController;
import com.evist0.application.AppView;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class Menubar extends JMenuBar {
    private final AppView _view;

    private final JMenuItem _startItem;
    private final JMenuItem _toggleTimerItem;
    private final JMenuItem _stopItem;

    public Menubar(AppController controller, AppView view) {
        super();
        _view = view;

        var controlsMenu = new JMenu("Управление");
        controlsMenu.setMnemonic(KeyEvent.VK_E);

        _startItem = new JMenuItem("Запуск");
        _startItem.setMnemonic(KeyEvent.VK_B);

        _toggleTimerItem = new JMenuItem("Таймер");
        _toggleTimerItem.setMnemonic(KeyEvent.VK_T);

        _stopItem = new JMenuItem("Стоп");
        _stopItem.setMnemonic(KeyEvent.VK_E);

        controlsMenu.add(_startItem);
        controlsMenu.add(_toggleTimerItem);
        controlsMenu.add(_stopItem);

        add(controlsMenu);

        _initListeners(controller);
    }

    private void _initListeners(AppController controller) {
        _startItem.addActionListener((e) -> {
            var dto = _view.getSettingsDTO();
            controller.start(dto);
        });

        _toggleTimerItem.addActionListener((e -> controller.toggleTimer()));

        _stopItem.addActionListener((e -> controller.stop()));
    }
}
