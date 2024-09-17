package Vista;

import Base.DatabaseManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PacienteOdontograma extends JFrame {

    private final String nombre;
    private final String apellido;
    private final String fecha;
    private final String dni;
    private final DefaultTableModel tableModel;

    private final JTextArea antecedentesArea; // Campo para antecedentes
    private JPanel panelCuadrante1;
    private JPanel panelCuadrante2;
    private JPanel panelCuadrante3;
    private JPanel panelCuadrante4;

    public PacienteOdontograma(String nombre, String apellido, String fecha, String dni, DefaultTableModel tableModel) {
        super("Modificar Odontograma");
        this.nombre = nombre;
        this.apellido = apellido;
        this.fecha = fecha;
        this.dni = dni;
        this.tableModel = tableModel;

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // Antecedentes médicos
        JLabel antecedentesLabel = new JLabel("Antecedentes Médicos:");
        antecedentesArea = new JTextArea(5, 20);
        JScrollPane antecedentesScrollPane = new JScrollPane(antecedentesArea);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(10, 10, 10, 10);
        add(antecedentesLabel, constraints);

        constraints.gridy = 1;
        add(antecedentesScrollPane, constraints);

        // Odontograma
        constraints.gridy = 2;
        JPanel odontogramaPanel = createOdontogramaPanel();
        add(odontogramaPanel, constraints);

        // Save button
        JButton saveButton = new JButton("Guardar");
        saveButton.addActionListener(new SaveButtonListener());
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        add(saveButton, constraints);

        // Set the size of the window
        setSize(600, 600);  // Set the desired width and height
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private JPanel createOdontogramaPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2, 10, 10)); // 2x2 grid for quadrants

        // Create quadrants
        panelCuadrante1 = createQuadrant(new int[]{18, 17, 16, 15, 14, 13, 12, 11, 48, 47, 46, 45, 44, 43, 42, 41}, "Cuadrante 1");
        panelCuadrante2 = createQuadrant(new int[]{21, 22, 23, 24, 25, 26, 27, 28, 31, 32, 33, 34, 35, 36, 37, 38}, "Cuadrante 2");
        panelCuadrante3 = createQuadrant(new int[]{55, 54, 53, 52, 51, 85, 84, 83, 82, 81}, "Cuadrante 3");
        panelCuadrante4 = createQuadrant(new int[]{61, 62, 63, 64, 65, 71, 72, 73, 74, 75}, "Cuadrante 4");

        panel.add(panelCuadrante1);
        panel.add(panelCuadrante2);
        panel.add(panelCuadrante3);
        panel.add(panelCuadrante4);

        panel.setBorder(BorderFactory.createTitledBorder("Odontograma"));
        return panel;
    }

    private JPanel createQuadrant(int[] teeth, String title) {
        JPanel quadrant = new JPanel(new GridLayout(4, 4, 5, 5)); // Adjust grid size based on teeth count
        for (int tooth : teeth) {
            JButton toothButton = new JButton(String.valueOf(tooth));
            toothButton.addActionListener(e -> openToothDescriptionWindow(tooth, toothButton));
            quadrant.add(toothButton);
        }
        quadrant.setBorder(BorderFactory.createTitledBorder(title));
        return quadrant;
    }

    private void openToothDescriptionWindow(int tooth, JButton toothButton) {
        JFrame descriptionFrame = new JFrame("Descripción del diente " + tooth);
        descriptionFrame.setLayout(new BorderLayout());

        JLabel toothLabel = new JLabel("Diente " + tooth);
        JTextArea descriptionArea = new JTextArea(10, 20);
        JButton saveButton = new JButton("Guardar");

        // Cargar la descripción existente si está disponible
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (Integer.parseInt(tableModel.getValueAt(i, 0).toString()) == tooth) {
                descriptionArea.setText(tableModel.getValueAt(i, 1).toString());
                break;
            }
        }

        saveButton.addActionListener(e -> {
            String description = descriptionArea.getText();

            // Actualizar modelo y base de datos
            int rowIndexToUpdate = findRowIndex(tooth);
            if (rowIndexToUpdate != -1) {
                tableModel.setValueAt(description, rowIndexToUpdate, 1);
            } else {
                tableModel.addRow(new Object[]{tooth, description});
            }
            String antecedentes = antecedentesArea.getText();
            boolean updated = DatabaseManager.updatePatient(nombre, apellido, fecha, dni, tableModel, antecedentes);

            if (updated) {
                JOptionPane.showMessageDialog(PacienteOdontograma.this, "Datos actualizados correctamente.");
            } else {
                JOptionPane.showMessageDialog(PacienteOdontograma.this, "Error al actualizar los datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            // Cambiar el color del botón para indicar que se guardó
            toothButton.setBackground(Color.GREEN);
            toothButton.setOpaque(true);
            toothButton.setBorderPainted(false);
            toothButton.repaint();

            descriptionFrame.dispose();
        });

        descriptionFrame.add(toothLabel, BorderLayout.NORTH);
        descriptionFrame.add(new JScrollPane(descriptionArea), BorderLayout.CENTER);
        descriptionFrame.add(saveButton, BorderLayout.SOUTH);

        descriptionFrame.pack();
        descriptionFrame.setLocationRelativeTo(this);
        descriptionFrame.setVisible(true);
    }

    private int findRowIndex(int tooth) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (Integer.parseInt(tableModel.getValueAt(i, 0).toString()) == tooth) {
                return i;
            }
        }
        return -1;
    }

    private class SaveButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Actualizar antecedentes médicos y datos del paciente en la base de datos
            String antecedentes = antecedentesArea.getText();
            boolean updated = DatabaseManager.updatePatient(nombre, apellido, fecha, dni, tableModel, antecedentes);

            if (updated) {
                JOptionPane.showMessageDialog(PacienteOdontograma.this, "Datos actualizados correctamente.");
            } else {
                JOptionPane.showMessageDialog(PacienteOdontograma.this, "Error al actualizar los datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

