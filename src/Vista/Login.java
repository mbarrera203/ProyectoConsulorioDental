package Vista;

import Base.DatabaseManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public Login() {
        super("Login");
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        JLabel usernameLabel = new JLabel("Usuario:");
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

        JButton loginButton = new JButton("Iniciar Sesión");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Verificar las credenciales en la base de datos
                boolean loggedIn = DatabaseManager.checkUser(username, password);
                if (loggedIn) {
                    // Cerrar la ventana de login
                    dispose();
 
                    // Abrir el nuevo JFrame
                    SwingUtilities.invokeLater(() -> {
                        Index indexFrame = new Index();
                        indexFrame.setVisible(true); 
                    });
                } else {
                    JOptionPane.showMessageDialog(Login.this, "Credenciales incorrectas.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(20, 10, 10, 10);
        constraints.anchor = GridBagConstraints.CENTER;
        add(loginButton, constraints);

        JButton createUserButton = new JButton("Crear Usuario");
        createUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateUserFrame();
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.anchor = GridBagConstraints.CENTER;
        add(createUserButton, constraints);

        setSize(400, 250); // Ajusta el tamaño según necesites
        setLocationRelativeTo(null); // Centrar la ventana en pantalla
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
