package com.evist0.client.views.results;

import com.evist0.client.AppController;
import com.evist0.client.models.AppModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ResultsDialog extends JDialog {
    private final AppController _controller;

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea textArea;

    public ResultsDialog(AppController controller, AppModel model) {
        super();

        setModal(true);
        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonOK);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        _controller = controller;

        textArea.setFont(new Font("Arial", Font.BOLD, 22));

        var text =
                "Физических лиц сгенерировано: %d\n Юридических лиц сгенерировано: %d\n\n Секунд затрачено: %d"
                        .formatted(model.getIndividualGenerated(), model.getCompanyGenerated(), model.getTimePassed());

        textArea.setText(text);

        __initListeners();
        setBounds(300, 300, 500, 500);
        pack();
        setVisible(true);
    }

    private void onCancel() {
        _controller.resumeSimulation();
        dispose();
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
