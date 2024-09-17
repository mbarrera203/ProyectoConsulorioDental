package Base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

public class DatabaseManager {

    // Datos de conexión a la base de datos
    private static final String URL = "jdbc:mysql://localhost:3308/consultorio";
    private static final String USER = "root";
    private static final String PASSWORD = "Vulcano12";

    // Método para insertar un nuevo usuario en la base de datos
    public static boolean insertUser(String username, String password) {
        boolean inserted = false;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "INSERT INTO usuarios (username, password) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                inserted = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inserted;
    }

    // Método para verificar si un usuario existe en la base de datos
    public static boolean checkUser(String username, String password) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT * FROM usuarios WHERE username = ? AND password = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // Devuelve true si encuentra un usuario con las credenciales dadas
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para insertar un paciente en la base de datos
    public static boolean insertPatientAndToothDescription(String nombre, String apellido, String fecha, String dni, int tooth, String description, String antecedentes) {
        boolean inserted = false;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "INSERT INTO pacientes (nombre, apellido, fecha, dni, tooth_id, description, antecedentes) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, nombre);
            statement.setString(2, apellido);
            statement.setString(3, fecha);
            statement.setString(4, dni);
            statement.setInt(5, tooth);
            statement.setString(6, description);
            statement.setString(7, antecedentes);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                inserted = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inserted;
    }

    public static ResultSet searchPatientByDni(String dni) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            String query = "SELECT * FROM pacientes WHERE dni = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, dni);
            rs = stmt.executeQuery();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Método para actualizar la información de un paciente
// Método para actualizar la información de un paciente
public static boolean updatePatient(String nombre, String apellido, String fecha, String dni, DefaultTableModel tableModel, String antecedentes) {
    boolean updated = false;
    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
        // Update patient details
        String query = "UPDATE pacientes SET nombre = ?, apellido = ?, fecha = ?, antecedentes = ? WHERE dni = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, nombre);
        statement.setString(2, apellido);
        statement.setString(3, fecha);
        statement.setString(4, antecedentes); // El cuarto parámetro es antecedentes
        statement.setString(5, dni); // El quinto parámetro es dni

        int rowsUpdated = statement.executeUpdate();

        if (rowsUpdated > 0) {
            // Update tooth descriptions
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                int tooth = Integer.parseInt(tableModel.getValueAt(i, 0).toString());
                String description = tableModel.getValueAt(i, 1).toString();

                // Check if the tooth description exists
                query = "SELECT COUNT(*) FROM pacientes WHERE dni = ? AND tooth_id = ?";
                statement = conn.prepareStatement(query);
                statement.setString(1, dni);
                statement.setInt(2, tooth);
                ResultSet rs = statement.executeQuery();
                rs.next();
                int count = rs.getInt(1);

                if (count > 0) {
                    // Update existing tooth description
                    query = "UPDATE pacientes SET description = ? WHERE dni = ? AND tooth_id = ?";
                    statement = conn.prepareStatement(query);
                    statement.setString(1, description);
                    statement.setString(2, dni);
                    statement.setInt(3, tooth);
                } else {
                    // Insert new tooth description
                    query = "INSERT INTO pacientes (nombre, apellido, fecha, dni, tooth_id, description, antecedentes) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    statement = conn.prepareStatement(query);
                    statement.setString(1, nombre);
                    statement.setString(2, apellido);
                    statement.setString(3, fecha);
                    statement.setString(4, dni);
                    statement.setInt(5, tooth);
                    statement.setString(6, description);
                    statement.setString(7, antecedentes);
                }
                statement.executeUpdate();
            }
            updated = true;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return updated;
}


}
