package qwe;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.table.AbstractTableModel;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

class NewTableModel extends AbstractTableModel {

    private final String[] columnNames = { "Model", "Typ", "Status", "Akcja" };
    private final ArrayList<Aircraft> data;

    public NewTableModel(String[] columnNames) {
        this.data = new ArrayList<>();
    };

    public void addRow(Aircraft a) {
        int newRow = this.data.size();
        this.data.add(a);
        fireTableRowsInserted(newRow, newRow);
    }

    @Override
    public int getColumnCount() {
        return this.columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Aircraft a = data.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> a.getModel();
            case 1 -> a.getClass().getSimpleName();
            case 2 -> a.getStatus();
            case 3 -> "Akcja";
            default -> null;
        };

    }

    @Override
    public int getRowCount() {
        return data.size();
    }

}

public class Gui {

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("AirportApp");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(1440, 900);

        frame.setLayout(new BorderLayout());

        String[] cols = { "Model", "Typ", "Status", "Akcja" };

        PassengerPlane plane = new PassengerPlane("Airbus A320", 240, 123);

        NewTableModel model = new NewTableModel(cols);
        model.addRow(plane);
        JTable table = new JTable(model);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton button = new JButton("Dodaj");
        buttonPanel.add(button);
        button.addActionListener((ActionEvent _) -> {
            JDialog dialog = new JDialog(frame);
            dialog.setSize(1440 / 2, 900 / 2);
            dialog.setLayout(new BorderLayout());

            JPanel row1 = new JPanel();
            JLabel fieldModel = new JLabel("Model: ");
            JTextField textField = new JTextField(30);
            row1.add(fieldModel);
            row1.add(textField);

            JPanel row2 = new JPanel();
            JLabel fieldCapacity = new JLabel("Liczba miejsc: ");
            JTextField field2 = new JTextField(30);
            row2.add(fieldCapacity);
            row2.add(field2);

            JPanel row3 = new JPanel();
            JLabel fieldSpeed = new JLabel("Prędkość: ");
            JTextField field3 = new JTextField(30);
            row3.add(fieldSpeed);
            row3.add(field3);

            JPanel container = new JPanel();
            container.add(row1);
            container.add(row2);
            container.add(row3);

            dialog.add(container);

            JButton create = new JButton("Stwórz");
            create.addActionListener((ActionEvent _) -> {
                PassengerPlane newPlane = new PassengerPlane(textField.getText(),
                        Double.parseDouble(field2.getText()),
                        Double.parseDouble(field3.getText()));
                model.addRow(newPlane);
                model.fireTableRowsInserted(0, model.getRowCount() - 1);
                dialog.dispose();

            });
            dialog.add(create, BorderLayout.SOUTH);
            dialog.setVisible(true);

        });
        button.setPreferredSize(new Dimension(200, 50));
        JScrollPane scrollpane = new JScrollPane(table);
        frame.add(scrollpane);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }
}
