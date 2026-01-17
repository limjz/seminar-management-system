package views;

import controllers.StudentController;
import java.awt.*;
import java.io.File;
import javax.swing.*;
import models.User;
import utils.Config;

public class StudentDashboard extends JFrame {

    private User currentUser;
    private StudentController controller;

    // --- FIX IS HERE: Constructor must accept 'User', not Strings ---
    public StudentDashboard(User user) {
        super("Student Dashboard");
        this.currentUser = user;
        this.controller = new StudentController();

        setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        Dimension btnSize = new Dimension(175, 25);

        // Display Student Name
        JLabel welcomeLabel = new JLabel("Welcome, " + currentUser.getUserName() + " (" + currentUser.getUserID() + ")");
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(welcomeLabel, gbc);

        // Buttons
        JButton registerSessionButton = new JButton("Register Session");
        registerSessionButton.setPreferredSize(btnSize);
        gbc.gridy = 1;
        panel.add(registerSessionButton, gbc);

        JButton getSessionButton = new JButton("Get Session");
        getSessionButton.setPreferredSize(btnSize);
        gbc.gridy = 2;
        panel.add(getSessionButton, gbc);

        JButton uploadMaterialButton = new JButton("Upload Material");
        uploadMaterialButton.setPreferredSize(btnSize);
        gbc.gridy = 3;
        panel.add(uploadMaterialButton, gbc);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setPreferredSize(btnSize);
        gbc.gridy = 4;
        panel.add(logoutButton, gbc);

        // Action Listeners
        registerSessionButton.addActionListener(e -> {
            RegisterSessionPage rsp = new RegisterSessionPage(currentUser, this);
            rsp.setVisible(true);
            this.setVisible(false);
        });

        getSessionButton.addActionListener(e -> {
             ViewSessionPage vsp = new ViewSessionPage(this); 
             vsp.setVisible(true);
             this.setVisible(false); 
        });

        uploadMaterialButton.addActionListener(e -> {
             // Open the new Material Upload Page
             MaterialUploadPage uploadPage = new MaterialUploadPage(currentUser, this);
             uploadPage.setVisible(true);
             this.setVisible(false);
        }
    );

        logoutButton.addActionListener(e -> dispose());

        add(panel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}