package com.evist0.application;

import com.evist0.dto.settings.SettingsException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ResultsDialog extends JDialog {
    private final AppModel _model;
    private final AppView _view;
    private final AppController _controller;

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea textArea;

    public ResultsDialog(AppView view, AppController controller, AppModel model) {
        super();

        setModal(true);
        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonOK);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        _model = model;
        _view = view;
        _controller = controller;

        textArea.setFont(new Font("Arial", Font.BOLD, 22));

        var text =
                "Физических лиц сгенерировано: %d\n Юридических лиц сгенерировано: %d\n\n Секунд затрачено: %d"
                        .formatted(_model.getIndividualGenerated(), _model.getCompanyGenerated(), _model.getTimePassed());

        textArea.setText(text);

        __initListeners();
        pack();
        setVisible(true);
    }

    private void onCancel() {
        try {
            var dto = _view.getSettingsDTO();
            _controller.resumeSimulation(dto);
            dispose();
        } catch (SettingsException e) {
            switch (e.getExceptionField()) {
                case N1 -> _model.setN1(1);
                case N2 -> _model.setN2(1);
            }
            _view.showErrorMessage(e.getMessage());
        }
    }

    private void __initListeners() {
        buttonOK.addActionListener(e -> dispose());
        buttonCancel.addActionListener(e -> onCancel());

        contentPane.registerKeyboardAction(
                e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        );

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
    }
}
