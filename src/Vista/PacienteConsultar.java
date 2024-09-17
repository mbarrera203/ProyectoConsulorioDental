package Vista;

import Base.DatabaseManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class PacienteConsultar extends JFrame implements ActionListener {

    private final JLabel titleLabel = new JLabel("Consultar Paciente:");
    private final JLabel dniLabel = new JLabel("DNI:");
    private final JTextField dniField = new JTextField(20);
    private final JButton searchButton = new JButton("Buscar");
    private final JButton saveButton = new JButton("Guardar");
    private final JButton modifyButton = new JButton("Modificar");
    private final JButton cancelButton = new JButton("Volver al Inicio");

    private final JLabel nombreLabel = new JLabel("Nombre:");
    private final JTextField nombreField = new JTextField(20);
    private final JLabel apellidoLabel = new JLabel("Apellido:");
    private final JTextField apellidoField = new JTextField(20);
    private final JLabel fechaLabel = new JLabel("Fecha de Comienzo del Tratamiento:");
    private final JTextField fechaField = new JTextField(20);
    private final JLabel antecedentesLabel = new JLabel("Antecedentes Clínicos:");
    private final JTextField antecedentesField = new JTextField(20);

    private final JTable table = new JTable();
    private final DefaultTableModel tableModel = new DefaultTableModel();

    public PacienteConsultar() {
        super("Consultar Paciente");
        // Set layout manager
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // Title label
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 3;
        constraints.insets = new Insets(20, 10, 20, 10);
        constraints.anchor = GridBagConstraints.CENTER;
        add(titleLabel, constraints);

        // DNI label and field
        constraints.gridwidth = 1;
        constraints.insets = new Insets(5, 10, 5, 10);
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(dniLabel, constraints);

        constraints.gridx = 1;
        add(dniField, constraints);

        // Search button
        constraints.gridx = 2;
        constraints.gridy = 1;
        add(searchButton, constraints);

        // Nombre label and field
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(nombreLabel, constraints);

        constraints.gridx = 1;
        add(nombreField, constraints);

        // Apellido label and field
        constraints.gridx = 0;
        constraints.gridy = 3;
        add(apellidoLabel, constraints);

        constraints.gridx = 1;
        add(apellidoField, constraints);

        // Fecha de comienzo del tratamiento label and field
        constraints.gridx = 0;
        constraints.gridy = 4;
        add(fechaLabel, constraints);

        constraints.gridx = 1;
        add(fechaField, constraints);

        // Antecedentes clínicos label and field
        constraints.gridx = 0;
        constraints.gridy = 5;
        add(antecedentesLabel, constraints);

        constraints.gridx = 1;
        add(antecedentesField, constraints);

        // Table for teeth and descriptions
        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.gridwidth = 3;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        add(new JScrollPane(table), constraints);

        // Set up table model
        tableModel.addColumn("Diente");
        tableModel.addColumn("Descripción");
        table.setModel(tableModel);

        // Save, Modify and Cancel buttons
        constraints.gridwidth = 1;
        constraints.insets = new Insets(10, 10, 10, 5);
        constraints.gridx = 0;
        constraints.gridy = 7;
        add(saveButton, constraints);

        constraints.gridx = 1;
        add(modifyButton, constraints);

        constraints.insets = new Insets(10, 5, 10, 10);
        constraints.gridx = 2;
        add(cancelButton, constraints);

        // Add action listeners to buttons
        searchButton.addActionListener(this);
        saveButton.addActionListener(this);
        modifyButton.addActionListener(this);
        cancelButton.addActionListener(this);

        // Disable fields initially
        setFieldsEditable(false);

        // Set the size of the window
        setSize(900, 800);  // Set the desired width and height
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void setFieldsEditable(boolean editable) {
        nombreField.setEditable(editable);
        apellidoField.setEditable(editable);
        fechaField.setEditable(editable);
        antecedentesField.setEditable(editable);
        table.setEnabled(editable);
    }

    private void clearTable() {
        tableModel.setRowCount(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            String dni = dniField.getText();
            try {
                ResultSet rs = DatabaseManager.searchPatientByDni(dni);
                if (rs != null && rs.next()) {
                    nombreField.setText(rs.getString("nombre"));
                    apellidoField.setText(rs.getString("apellido"));
                    fechaField.setText(rs.getString("fecha"));
                    antecedentesField.setText(rs.getString("antecedentes"));

                    clearTable();

                    do {
                        Vector<Object> row = new Vector<>();
                        row.add(rs.getString("tooth_id"));
                        row.add(rs.getString("description"));
                        tableModel.addRow(row);
                    } while (rs.next());

                    setFieldsEditable(false); // Disable fields after fetching data
                } else {
                    JOptionPane.showMessageDialog(this, "Paciente no encontrado!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == saveButton) {
            String nombre = nombreField.getText();
            String apellido = apellidoField.getText();
            String fecha = fechaField.getText();
            String dni = dniField.getText();
            String antecedentes = antecedentesField.getText();

            boolean updated = DatabaseManager.updatePatient(nombre, apellido, fecha, dni, tableModel, antecedentes);
            if (updated) {
                JOptionPane.showMessageDialog(this, "Información del paciente actualizada con éxito!");
                setFieldsEditable(false); // Disable fields after saving
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar la información del paciente.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == modifyButton) {
            // Abrir la ventana del odontograma para modificar
            String nombre = nombreField.getText();
            String apellido = apellidoField.getText();
            String fecha = fechaField.getText();
            String dni = dniField.getText();
            String antecedentes = antecedentesField.getText();

            new PacienteOdontograma(nombre, apellido, fecha, dni, tableModel);
        } else if (e.getSource() == cancelButton) {
            // Close the window
            dispose();
            SwingUtilities.invokeLater(() -> {
                Index index = new Index();
                index.setVisible(true);
            });
        }
    }
}



