import javax.swing.*;

public class LoginFrame extends JFrame {
    private JTextField userField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginFrame() {
        setTitle("Login");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel userLabel = new JLabel("Usuário:");
        userLabel.setBounds(10, 10, 80, 25);
        add(userLabel);

        userField = new JTextField();
        userField.setBounds(100, 10, 160, 25);
        add(userField);

        JLabel passwordLabel = new JLabel("Senha:");
        passwordLabel.setBounds(10, 40, 80, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(100, 40, 160, 25);
        add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(100, 80, 160, 25);
        add(loginButton);

        loginButton.addActionListener(e -> {
            String usuario = userField.getText();
            String senha = new String(passwordField.getPassword());

            if (MySQLConnection.validarLogin(usuario, senha)) {
                JOptionPane.showMessageDialog(this, "Login bem-sucedido");

                // Assuming you retrieve the `alunoId` based on the login credentials
                int alunoId = MySQLConnection.getAlunoId(usuario); // Example method

                // Dispose the login frame and open the NotasFrame
                this.dispose(); // Closes the LoginFrame

                NotasFrame notasFrame = new NotasFrame(alunoId);
                notasFrame.setVisible(true); // Open the NotasFrame
            } else {
                JOptionPane.showMessageDialog(this, "Usuário ou senha inválidos");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame login = new LoginFrame();
            login.setVisible(true);
        });
    }
}
