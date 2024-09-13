import java.sql.*;

public class MySQLConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/imtnotas";
    private static final String USER = "root";
    private static final String PASS = "goodpassword";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // Validate login
    public static boolean validarLogin(String usuario, String senha) {
        String query = "SELECT * FROM alunos WHERE usuario = ? AND senha = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, usuario);
            ps.setString(2, senha);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Retrieve alunoId based on the username
    public static int getAlunoId(String usuario) {
        String query = "SELECT id FROM alunos WHERE usuario = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, usuario);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if not found or an error occurs
    }
}
