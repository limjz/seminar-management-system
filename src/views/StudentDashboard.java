package views;

import java.awt.*;
import javax.swing.*;
import utils.Config;

public class StudentDashboard extends JFrame {

    // Constructor accepts student details to display them
    public StudentDashboard(String studentName, String studentId) {
        super("Student Dashboard");
        setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10); // add spacing between buttons
        gbc.anchor = GridBagConstraints.CENTER; // center the buttons 
        Dimension btnSize = new Dimension(175, 25); // match button size from CoordinatorDashboard

        // Display Student Name Label
        JLabel welcomeLabel = new JLabel("Welcome, " + studentName + " (" + studentId + ")"); //need to read from database
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(welcomeLabel, gbc);

        // Register Session Button
        JButton registerSessionButton = new JButton("Register Session");
        registerSessionButton.setPreferredSize(btnSize);
        gbc.gridy = 1;
        panel.add(registerSessionButton, gbc);

        // Get Session Button
        JButton getSessionButton = new JButton("Get Session");
        getSessionButton.setPreferredSize(btnSize);
        gbc.gridy = 2;
        panel.add(getSessionButton, gbc);

        // Upload Material Button
        JButton uploadMaterialButton = new JButton("Upload Material");
        uploadMaterialButton.setPreferredSize(btnSize);
        // fourth row
        gbc.gridy = 3;
        panel.add(uploadMaterialButton, gbc);

        // Logout Button (Standard for dashboards)
        JButton logoutButton = new JButton("Logout");
        logoutButton.setPreferredSize(btnSize);
        // fifth row
        gbc.gridy = 4;
        panel.add(logoutButton, gbc);

        //--------------------------- action listeners ----------------------------
        // Logic left empty as requested
        
        registerSessionButton.addActionListener(e -> {
            // TODO: Link to Register Presentation View
        });

        getSessionButton.addActionListener(e -> {
             // Pass 'this' so ViewSessionPage knows to come back to StudentDashboard
             ViewSessionPage vsp = new ViewSessionPage(this); 
             vsp.setVisible(true);
             this.setVisible(false); // Hide the dashboard temporarily
        });

        uploadMaterialButton.addActionListener(e -> {
             // TODO: Link to File Chooser/Upload Logic
        });

        logoutButton.addActionListener(e -> {
             this.dispose();
             // TODO: Return to LoginMainPage
        });

        add(panel);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}