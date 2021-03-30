package com.evist0.components.objects;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.HashMap;
import java.util.UUID;

public class ObjectsDialog extends JDialog {
    private JPanel contentPane;

    private final Object[] tableHeader = new String[]{"ID", "Время рождения"};
    private final HashMap<UUID, Long> tableData;
    private JTable table;

    public ObjectsDialog(HashMap<UUID, Long> tableData) {
        super();

        this.tableData = tableData;

        setModal(true);
        setContentPane(contentPane);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setBounds(300, 300, 500, 500);
        setVisible(true);
    }

    private void createUIComponents() {
        var model = new DefaultTableModel();

        model.setColumnIdentifiers(tableHeader);

        for (var entry : tableData.entrySet()) {
            model.addRow(new Object[]{entry.getKey(), entry.getValue()});
        }

        table = new JTable(model);
        table.setShowGrid(true);
        table.setAutoCreateRowSorter(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        var columnModel = table.getColumnModel();

        columnModel.getColumn(0).setPreferredWidth(300);
    }
}
