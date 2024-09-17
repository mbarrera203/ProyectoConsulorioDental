package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Index extends JFrame implements ActionListener {

    private final JLabel welcomeLabel = new JLabel("Bienvenida Doctora");
    private final JButton newPatientButton = new JButton("Nuevo Paciente");
    private final JButton consultPatientButton = new JButton("Consultar Paciente");
    private final JButton exitButton = new JButton("Salir");

    public Index() {
        super("Consultorio Odontológico");
        // Set layout manager
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // Welcome label
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(20, 10, 20, 10);
        constraints.anchor = GridBagConstraints.CENTER;
        add(welcomeLabel, constraints);

        // New Patient button
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.insets = new Insets(10, 10, 10, 10);
        add(newPatientButton, constraints);

        // Consult Patient button
        constraints.gridx = 1;
        add(consultPatientButton, constraints);

        // Exit button
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.SOUTHEAST;
        constraints.insets = new Insets(10, 10, 20, 10);
        add(exitButton, constraints);

        // Add action listeners to buttons
        newPatientButton.addActionListener(this);
        consultPatientButton.addActionListener(this);
        exitButton.addActionListener(this);

        // Set the size of the window
        setSize(400, 300);  // Set the desired width and height
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newPatientButton) {
             // Cerrar la ventana de login
                    dispose();
                    // Abrir el nuevo JFrame
                    SwingUtilities.invokeLater(() -> {
                        PacienteNuevo pn = new PacienteNuevo();
                        pn.setVisible(true);
                    });
        } else if (e.getSource() == consultPatientButton) {
            // Implement consult patient logic here
              dispose();
                    // Abrir el nuevo JFrame
                    SwingUtilities.invokeLater(() -> {
                        PacienteConsultar pc = new PacienteConsultar();
                        pc.setVisible(true);
                    });
        } else if (e.getSource() == exitButton) {
            // Exit the application
            dispose();
            SwingUtilities.invokeLater(()->{
                Login lg = new Login();
                lg.setVisible(true);
            });
        }
    }  
}
