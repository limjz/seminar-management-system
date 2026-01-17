package views;

import controllers.StudentController;
import models.User;
import utils.Config;

import javax.swing.*;
import java.awt.*;

public class RegisterSessionPage extends JFrame {

    private User student;
    private JFrame previousScreen;
    private StudentController controller;

    public RegisterSessionPage(User student, JFrame previousScreen) {
        super("Register Presentation");
        this.student = student;
        this.previousScreen = previousScreen;
        this.controller = new StudentController();

        setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- Fields ---

        // Title
        gbc.gridx = 0; gbc.gridy = 0; add(new JLabel("Research Title:"), gbc);
        JTextField txtTitle = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 0; add(txtTitle, gbc);

        // Abstract
        gbc.gridx = 0; gbc.gridy = 1; add(new JLabel("Abstract:"), gbc);
        JTextArea txtAbstract = new JTextArea(3, 20);
        JScrollPane scrollAbs = new JScrollPane(txtAbstract);
        gbc.gridx = 1; gbc.gridy = 1; add(scrollAbs, gbc);

        // Supervisor Name
        gbc.gridx = 0; gbc.gridy = 2; add(new JLabel("Supervisor:"), gbc);
        JTextField txtSupervisor = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 2; add(txtSupervisor, gbc);

        // Type
        gbc.gridx = 0; gbc.gridy = 3; add(new JLabel("Type:"), gbc);
        JComboBox<String> cmbType = new JComboBox<>(new String[]{"Oral", "Poster"});
        gbc.gridx = 1; gbc.gridy = 3; add(cmbType, gbc);

        // Session Dropdown
        gbc.gridx = 0; gbc.gridy = 4; add(new JLabel("Session:"), gbc);
        
        // Load sessions from Controller
        String[] sessions = controller.getAvailableSession();
        JComboBox<String> cmbSession = new JComboBox<>(sessions);
        
        gbc.gridx = 1; gbc.gridy = 4; add(cmbSession, gbc);

        // --- Buttons ---
        JPanel btnPanel = new JPanel();
        JButton btnSubmit = new JButton("Submit");
        JButton btnCancel = new JButton("Cancel");
        btnPanel.add(btnSubmit);
        btnPanel.add(btnCancel);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        add(btnPanel, gbc);

        // --- Logic ---

        btnCancel.addActionListener(e -> {
            dispose();
            if (previousScreen != null) previousScreen.setVisible(true);
        });

        btnSubmit.addActionListener(e -> {
            if(txtTitle.getText().isEmpty() || txtSupervisor.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields");
                return;
            }

            // Call Controller
            controller.registerPresentation(
                student.getUserID(),
                txtTitle.getText(),
                (String) cmbType.getSelectedItem(),
                txtAbstract.getText(),
                txtSupervisor.getText(),
                (String) cmbSession.getSelectedItem()
            );

            JOptionPane.showMessageDialog(this, "Registration Successful!");
            dispose();
            if (previousScreen != null) previousScreen.setVisible(true);
        });

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}