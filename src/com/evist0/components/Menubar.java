package com.evist0.components;

import com.evist0.application.AppController;
import com.evist0.application.AppModel;
import com.evist0.application.AppView;
import com.evist0.dto.settings.SettingsException;
import com.evist0.dto.settings.SettingsExceptionHandler;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class Menubar extends JMenuBar {
    private final AppView _view;

    private final JMenuItem _startItem;
    private final JMenuItem _toggleTimerItem;
    private final JMenuItem _stopItem;

    private final JMenuItem _showObjectsItem;

    public Menubar(AppController controller, AppView view, AppModel model) {
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

        _showObjectsItem = new JMenuItem("Объекты");

        add(controlsMenu);
        add(_showObjectsItem);

        _initListeners(controller, model);
    }

    private void _initListeners(AppController controller, AppModel model) {
        _startItem.addActionListener((event) -> {
            try {
                var dto = _view.getSettingsDTO();
                controller.startSimulation(dto);
            } catch (SettingsException exception) {
                var handler = new SettingsExceptionHandler(model);
                handler.handleException(exception);

                _view.showErrorMessage(exception.getMessage());
            }
        });
        _toggleTimerItem.addActionListener((e -> controller.toggleTimerVisible()));
        _stopItem.addActionListener((e -> controller.stopSimulation()));

        _showObjectsItem.addActionListener(e -> controller.showObjectsDialog());
    }
}
