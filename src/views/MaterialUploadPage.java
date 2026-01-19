package views;

import controllers.StudentController;
import java.awt.*;
import java.io.File;
import javax.swing.*;
import models.User;
import utils.Config;

public class MaterialUploadPage extends JFrame {

    private User student;
    private JFrame previousScreen;
    private StudentController controller;
    private File selectedFile; // To store the chosen file temporarily

    public MaterialUploadPage(User student, JFrame previousScreen) {
        super("Upload Presentation Material");
        this.student = student;
        this.previousScreen = previousScreen;
        this.controller = new StudentController();

        setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- 1. Select Session ---
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Select Session:"), gbc);

        JComboBox<String> cmbSessions = new JComboBox<>();
        
        // CALL THE NEW METHOD HERE
        String[] mySessions = controller.getRegisteredSessions(student.getUserID());
        
        if (mySessions.length == 0) {
            cmbSessions.addItem("No registrations found");
            cmbSessions.setEnabled(false);
        } else {
            for (String s : mySessions) {
                cmbSessions.addItem(s);
            }
        }
        
        gbc.gridx = 1; gbc.gridy = 0;
        add(cmbSessions, gbc);

        // --- 2. File Selection ---
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Presentation File:"), gbc);

        JPanel filePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        JButton btnChooseFile = new JButton("Choose File...");
        JLabel lblFileName = new JLabel(" No file selected");
        
        filePanel.add(btnChooseFile);
        filePanel.add(lblFileName);

        gbc.gridx = 1; gbc.gridy = 1;
        add(filePanel, gbc);

        // --- 3. Buttons ---
        JPanel btnPanel = new JPanel();
        JButton btnUpload = new JButton("Upload");
        JButton btnCancel = new JButton("Cancel");
        btnPanel.add(btnUpload);
        btnPanel.add(btnCancel);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        add(btnPanel, gbc);

        // --- Logic ---

        // File Chooser Logic
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

        // Upload Logic
        btnUpload.addActionListener(e -> {
            if (selectedFile == null) {
                JOptionPane.showMessageDialog(this, "Please select a file first.");
                return;
            }
            if (!cmbSessions.isEnabled()) {
                JOptionPane.showMessageDialog(this, "You have no registered sessions.");
                return;
            }

            String selectedSessionID = (String) cmbSessions.getSelectedItem();
            
            // Call Controller (Updated to accept Session Info)
            boolean success = controller.uploadMaterial(
                student.getUserID(), 
                selectedSessionID, 
                selectedFile.getAbsolutePath()
            );

            if (success) {
                JOptionPane.showMessageDialog(this, "Material uploaded successfully!");
                dispose();
                if (previousScreen != null) previousScreen.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Upload Failed. Please try again.");
            }
        });

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}