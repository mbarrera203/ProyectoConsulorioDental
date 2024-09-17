package Vista;

import Base.DatabaseManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PacienteNuevo extends JFrame implements ActionListener {

    private final JLabel titleLabel = new JLabel("Agregar Paciente Nuevo:");
    private final JLabel nombreLabel = new JLabel("Nombre:");
    private final JTextField nombreField = new JTextField(20);
    private final JLabel apellidoLabel = new JLabel("Apellido:");
    private final JTextField apellidoField = new JTextField(20);
    private final JLabel fechaLabel = new JLabel("Fecha de Comienzo del Tratamiento:");
    private final JTextField fechaField = new JTextField(20);
    private final JLabel dniLabel = new JLabel("DNI:");
    private final JTextField dniField = new JTextField(20);
    private final JLabel antecedentesClinicosLabel = new JLabel("Antecedentes Clínicos:"); // Definir el campo de antecedentes
    private final JTextField antecedentesClinicosField = new JTextField(20); // Definir el campo de texto para antecedentes
    private final JButton saveButton = new JButton("Guardar");
    private final JButton cancelButton = new JButton("Volver al Inicio");
    private JLabel imageLabel;

    private JPanel panelCuadrante1;
    private JPanel panelCuadrante2;
    private JPanel panelCuadrante3;
    private JPanel panelCuadrante4;

    public PacienteNuevo() {
        super("Agregar Paciente Nuevo");
        // Set layout manager
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // Title label
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(20, 10, 20, 10);
        constraints.anchor = GridBagConstraints.CENTER;
        add(titleLabel, constraints);

        // Nombre label and field
        constraints.gridwidth = 1;
        constraints.insets = new Insets(5, 10, 5, 10);
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(nombreLabel, constraints);
        constraints.gridx = 1;
        add(nombreField, constraints);

        // Apellido label and field
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(apellidoLabel, constraints);
        constraints.gridx = 1;
        add(apellidoField, constraints);

        // Fecha de comienzo del tratamiento label and field
        constraints.gridx = 0;
        constraints.gridy = 3;
        add(fechaLabel, constraints);
        constraints.gridx = 1;
        add(fechaField, constraints);

        // DNI label and field
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 4; // Cambiado a fila 4
        add(dniLabel, constraints);
        constraints.gridx = 1;
        add(dniField, constraints);

        // Antecedentes label and field
        constraints.gridx = 0;
        constraints.gridy = 5; // Cambiado a fila 5
        add(antecedentesClinicosLabel, constraints);
        constraints.gridx = 1;
        add(antecedentesClinicosField, constraints);

        // Imagen del odontograma
        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(10, 38, 10, 10);
        constraints.anchor = GridBagConstraints.CENTER;
        add(createImageLabel(), constraints);

        // Odontograma
        constraints.gridx = 0;
        constraints.gridy = 7;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(10, 10, 10, 10);
        JPanel odontogramaPanel = createOdontogramaPanel();
        add(odontogramaPanel, constraints);

        // Save and Cancel buttons
        constraints.gridwidth = 1;
        constraints.insets = new Insets(10, 10, 10, 5);
        constraints.gridx = 0;
        constraints.gridy = 8;
        add(saveButton, constraints);

        constraints.insets = new Insets(10, 5, 10, 10);
        constraints.gridx = 1;
        add(cancelButton, constraints);

        // Add action listeners to buttons
        saveButton.addActionListener(this);
        cancelButton.addActionListener(this);

        // Set the size of the window
        setSize(600, 900);  // Set the desired width and height
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private JLabel createImageLabel() {
        imageLabel = new JLabel();
        try {
            // Asume que la imagen está en el paquete "Media" dentro del directorio de src
            ImageIcon odontogramaImage = new ImageIcon(getClass().getResource("/Imagenes/Odontograma.png"));
            imageLabel.setIcon(odontogramaImage);
        } catch (Exception e) {
            e.printStackTrace();
            imageLabel.setText("Imagen no disponible");
        }
        return imageLabel;
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            // Recoger la información del paciente
            String nombre = nombreField.getText();
            String apellido = apellidoField.getText();
            String fecha = fechaField.getText();
            String dni = dniField.getText();
            String antecedentes = antecedentesClinicosField.getText(); // Recoger el valor de antecedentes

            if (nombre.isEmpty() || apellido.isEmpty() || fecha.isEmpty() || dni.isEmpty() || antecedentes.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete toda la información del paciente.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Paciente guardado con éxito", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (e.getSource() == cancelButton) {
            // Cerrar la ventana
            dispose();
            SwingUtilities.invokeLater(() -> {
                Index idex = new Index();
                idex.setVisible(true);
            });
        }
    }

    private JPanel createQuadrant(int[] teeth, String title) {
        JPanel quadrant = new JPanel(new GridLayout(4, 4, 5, 5)); // Adjust grid size based on teeth count
        for (int tooth : teeth) {
            JButton toothButton = new JButton(String.valueOf(tooth));
            toothButton.addActionListener(e -> {
                // Recoger la información del paciente
                String nombre = nombreField.getText();
                String apellido = apellidoField.getText();
                String fecha = fechaField.getText();
                String dni = dniField.getText();
                String antecedentes = antecedentesClinicosField.getText(); // Recoger el valor de antecedentes

                openToothDescriptionWindow(nombre, apellido, fecha, dni, tooth, toothButton, antecedentes);
            });
            quadrant.add(toothButton);
        }
        quadrant.setBorder(BorderFactory.createTitledBorder(title));
        return quadrant;
    }

    private void openToothDescriptionWindow(String nombre, String apellido, String fecha, String dni, int tooth, JButton toothButton, String antecedentes) {
        JFrame descriptionFrame = new JFrame("Descripción del diente " + tooth);
        descriptionFrame.setLayout(new BorderLayout());
        JLabel toothLabel = new JLabel("Diente " + tooth);
        JTextArea descriptionArea = new JTextArea(10, 20);
        JButton saveButton = new JButton("Guardar");

        saveButton.addActionListener(e -> {
            String description = descriptionArea.getText();

            if (nombre.isEmpty() || apellido.isEmpty() || fecha.isEmpty() || dni.isEmpty() || description.isEmpty() || antecedentes.isEmpty()) {
                JOptionPane.showMessageDialog(descriptionFrame, "Primero debe llenar la informacion del paciente y luego guardar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            } else {
                // Guardar la información del paciente y la descripción del diente en la base de datos
                boolean saved = DatabaseManager.insertPatientAndToothDescription(nombre, apellido, fecha, dni, tooth, description, antecedentes);

                if (saved) {
                    JOptionPane.showMessageDialog(descriptionFrame, "Paciente y descripción del diente guardados correctamente.");

                    // Cambiar el color del botón del diente seleccionado
                    toothButton.setBackground(Color.GREEN);
                    toothButton.setOpaque(true);
                    toothButton.setBorderPainted(false);

                    // Repintar el botón para reflejar el cambio de color
                    toothButton.repaint();
                } else {
                    JOptionPane.showMessageDialog(descriptionFrame, "Error al guardar los datos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            descriptionFrame.dispose();
        });

        descriptionFrame.add(toothLabel, BorderLayout.NORTH);
        descriptionFrame.add(new JScrollPane(descriptionArea), BorderLayout.CENTER);
        descriptionFrame.add(saveButton, BorderLayout.SOUTH);

        descriptionFrame.pack();
        descriptionFrame.setLocationRelativeTo(this);
        descriptionFrame.setVisible(true);
    }
}
