import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.sql.*;

public class NotasFrame extends JFrame {
    private JTable table;

    public NotasFrame(int alunoId) {
        setTitle("Notas e Faltas");
        setSize(800, 600); // Increase the window size to accommodate more content
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columnNames = {"Matéria", "Nota", "Faltas"};

        // Create a non-editable table model by overriding isCellEditable
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // All cells are non-editable
            }
        };

        table = new JTable(model);
        
        // Prevent column resizing and reordering by the user
        JTableHeader header = table.getTableHeader();
        header.setResizingAllowed(false);
        header.setReorderingAllowed(false);

        // Auto-resize columns based on content
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Add the table to a scroll pane and add it to the frame
        add(new JScrollPane(table));

        carregarDados(alunoId);
    }

    private void carregarDados(int alunoId) {
        String query = "SELECT m.nome, n.nota, f.faltas " +
                       "FROM materias m " +
                       "JOIN notas n ON m.id = n.id_materia " +
                       "JOIN faltas f ON m.id = f.id_materia " +
                       "WHERE n.id_aluno = ? AND f.id_aluno = ?";

        try (Connection con = MySQLConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, alunoId);
            ps.setInt(2, alunoId);

            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            while (rs.next()) {
                String materia = rs.getString("nome");
                double nota = rs.getDouble("nota");
                int faltas = rs.getInt("faltas");
                model.addRow(new Object[]{materia, nota, faltas});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
