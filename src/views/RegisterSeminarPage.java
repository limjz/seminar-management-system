package views;

import controllers.StudentController;
import java.awt.*;
import java.io.File;
import javax.swing.*;
import models.User;
import utils.Config;

public class RegisterSeminarPage extends JFrame {

    private User student;
    private JFrame previousScreen;
    private StudentController controller;
    private File selectedFile; 

    public RegisterSeminarPage(User student, JFrame previousScreen) {
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

        // Material Upload
        gbc.gridx = 0; gbc.gridy = 5; add(new JLabel("Presentation File:"), gbc);
        
        JPanel filePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        JButton btnChooseFile = new JButton("Choose File");
        JLabel lblFileName = new JLabel(" No file selected");
        filePanel.add(btnChooseFile);
        filePanel.add(lblFileName);
        
        gbc.gridx = 1; gbc.gridy = 5; add(filePanel, gbc);

        // --- Buttons ---
        JPanel btnPanel = new JPanel();
        JButton btnSubmit = new JButton("Submit");
        JButton btnCancel = new JButton("Cancel");
        btnPanel.add(btnSubmit);
        btnPanel.add(btnCancel);

        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        add(btnPanel, gbc);

        // --- Logic ---

        btnChooseFile.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                lblFileName.setText(" " + selectedFile.getName());
            }
        });

        btnCancel.addActionListener(e -> {
            dispose();
            if (previousScreen != null) previousScreen.setVisible(true);
        });

        btnSubmit.addActionListener(e -> {
            if(txtTitle.getText().isEmpty() || txtSupervisor.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields");
                return;
            }

            String filePath = (selectedFile != null) ? selectedFile.getAbsolutePath() : null;

            // Call Controller
            controller.registerPresentation(
                student.getUserID(),
                txtTitle.getText(),
                (String) cmbType.getSelectedItem(),
                txtAbstract.getText(),
                txtSupervisor.getText(),
                (String) cmbSession.getSelectedItem(),
                filePath
            );

            JOptionPane.showMessageDialog(this, "Registration Successful!");
            dispose();
            if (previousScreen != null) previousScreen.setVisible(true);
        });

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}