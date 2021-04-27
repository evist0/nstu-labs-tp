package com.evist0.client.views.app;

import com.evist0.client.AppController;
import com.evist0.client.models.AppModel;
import com.evist0.common.dto.settings.SettingsException;
import com.evist0.common.dto.settings.SettingsExceptionHandler;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class Menubar extends JMenuBar {
    private final AppView _view;

    private final JMenu _controlsMenu = new JMenu("Управление");
    private final JMenuItem _startItem = new JMenuItem("Запуск");
    private final JMenuItem _toggleTimerItem = new JMenuItem("Таймер");
    private final JMenuItem _stopItem = new JMenuItem("Стоп");

    private final JMenuItem _showObjectsItem = new JMenuItem("Показать объекты");

    private final JMenuItem _showConsoleItem = new JMenuItem("Консоль");

    private final JMenu _fileMenu = new JMenu("Файл");
    private final JMenuItem _saveItem = new JMenuItem("Сохранить");
    private final JMenuItem _loadItem = new JMenuItem("Загрузить");

    public Menubar(AppController controller, AppView view, AppModel model) {
        super();
        _view = view;

        _controlsMenu.setMnemonic(KeyEvent.VK_E);

        _startItem.setMnemonic(KeyEvent.VK_B);
        _toggleTimerItem.setMnemonic(KeyEvent.VK_T);
        _stopItem.setMnemonic(KeyEvent.VK_E);

        _controlsMenu.add(_startItem);
        _controlsMenu.add(_toggleTimerItem);
        _controlsMenu.add(_stopItem);

        add(_controlsMenu);

        add(_showObjectsItem);

        _fileMenu.add(_saveItem);
        _fileMenu.add(_loadItem);

        add(_fileMenu);

        add(_showConsoleItem);

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

        _saveItem.addActionListener(e -> controller.saveObjects());
        _loadItem.addActionListener(e -> controller.loadObjects());

        _showConsoleItem.addActionListener(e -> controller.toggleConsole());
    }

    public void repaintChildren() {
        _controlsMenu.repaint();
        _startItem.repaint();
        _toggleTimerItem.repaint();
        _stopItem.repaint();

        _showObjectsItem.repaint();

        _showConsoleItem.repaint();

        _fileMenu.repaint();
        _saveItem.repaint();
        _loadItem.repaint();
    }
}
