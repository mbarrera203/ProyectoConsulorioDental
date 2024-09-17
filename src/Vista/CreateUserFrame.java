package Vista;

import Base.DatabaseManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateUserFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public CreateUserFrame() {
        super("Crear Usuario");
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        JLabel usernameLabel = new JLabel("Nombre del Profesional:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 10, 10, 10);
        add(usernameLabel, constraints);

        usernameField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 0;
        add(usernameField, constraints);

        JLabel passwordLabel = new JLabel("Contraseña:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(passwordLabel, constraints);

        passwordField = new JPasswordField(20);
        constraints.gridx = 1;
        constraints.gridy = 1;
        add(passwordField, constraints);

        JButton saveButton = new JButton("Guardar");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Insertar usuario en la base de datos
                boolean inserted = DatabaseManager.insertUser(username, password);
                if (inserted) {
                    JOptionPane.showMessageDialog(CreateUserFrame.this, "Usuario creado exitosamente.");
                    dispose(); // Cerrar la ventana de creación de usuario
                } else {
                    JOptionPane.showMessageDialog(CreateUserFrame.this, "Error al crear usuario.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(20, 10, 10, 10);
        constraints.anchor = GridBagConstraints.CENTER;
        add(saveButton, constraints);

        setSize(500, 200); // Ajusta el tamaño según necesites
        setLocationRelativeTo(null); // Centrar la ventana en pantalla
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Cerrar solo la ventana actual al guardar
        setVisible(true);
    }
}

