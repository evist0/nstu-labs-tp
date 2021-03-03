package com.evist0.application;

import com.evist0.components.CanvasPane;
import com.evist0.components.Menubar;
import com.evist0.components.SettingsPane;
import com.evist0.components.Timer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class AppView extends JFrame {
    public final Menubar menubar;

    public final CanvasPane canvasPane = new CanvasPane();
    public final SettingsPane settingsPane;
    public final Timer timer = new Timer();

    public AppView(AppController controller, AppModel model) {
        super("Взлом жёппы");

        menubar = new Menubar(controller, this);
        settingsPane = new SettingsPane(controller, model);

        _initLayout();
        _initKeyListener(controller);
    }

    private void _initLayout() {
        setBounds(0, 0, 1280, 720);
        getContentPane().setLayout(new BorderLayout());

        var wrapper = new JPanel();
        wrapper.setLayout(new GridBagLayout());

        var gbc = new GridBagConstraints();

        gbc.weightx = 0;
        gbc.weighty = 1.0;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(16, 16, 16, 16);
        wrapper.add(settingsPane, gbc);

        gbc.weightx = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.RELATIVE;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 0);
        wrapper.add(canvasPane, gbc);

        canvasPane.setBackground(new Color(0xbbbbbb));

        getContentPane().add(timer);
        getContentPane().add(wrapper, BorderLayout.CENTER);

        setJMenuBar(menubar);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
    }

    private void _initKeyListener(AppController controller) {
        var keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                switch (e.getKeyChar()) {
                    case 'b' -> {
                        var dto = settingsPane.getSettingsDTO();
                        controller.start(dto);
                    }
                    case 't' -> controller.toggleTimer();
                    case 'e' -> controller.stop();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        };

        addKeyListener(keyListener);
    }
}