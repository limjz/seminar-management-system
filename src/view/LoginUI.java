package view;

import javax.swing.*;
import java.awt.*;

public class LoginUI extends JFrame {

    private JTextField txtEvaluatorId = new JTextField(15);

    public LoginUI() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 180);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panel.add(new JLabel("Evaluator ID:"));
        panel.add(txtEvaluatorId);

        JButton btnLogin = new JButton("Login");
        panel.add(new JLabel(""));
        panel.add(btnLogin);

        btnLogin.addActionListener(e -> doLogin());

        add(panel);
    }

    private void doLogin() {
        String evaluatorId = txtEvaluatorId.getText().trim();
        if (evaluatorId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Evaluator ID (example: EVA-001)");
            return;
        }

        // Open evaluation UI
        new EvaluationUI(evaluatorId).setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginUI().setVisible(true));
    }
}


