package views;

import controllers.StudentController;
import java.awt.*;
import javax.swing.*;
import models.User;
import utils.Config;


public class StudentDashboard extends JFrame {

    private User currentUser;
    private final StudentController controller;

    public StudentDashboard(User user) {
        super("Student Dashboard");
        this.currentUser = user;
        this.controller = new StudentController();

        setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);


        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        Dimension btnSize = new Dimension(175, 25);

        // Display Student Name
        JLabel welcomeLabel = new JLabel("Welcome, " + currentUser.getUserName() + " (" + currentUser.getUserID() + ")");
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(welcomeLabel, gbc);

        //---------- Buttons ------------
        JButton registerSeminarButton = new JButton("Register Seminar");
        registerSeminarButton.setPreferredSize(btnSize);
        gbc.gridy = 1;
        panel.add(registerSeminarButton, gbc);

        JButton getSessionButton = new JButton("View Registered Session");
        getSessionButton.setPreferredSize(btnSize);
        gbc.gridy = 2;
        panel.add(getSessionButton, gbc);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setPreferredSize(btnSize);
        gbc.gridy = 3;
        panel.add(logoutButton, gbc);

        //------------ Action Listeners ------------
        registerSeminarButton.addActionListener(e -> {
            RegisterSeminarPage rsp = new RegisterSeminarPage(currentUser, this);
            rsp.setVisible(true);
            this.setVisible(false);
        });

        getSessionButton.addActionListener(e -> {
             ViewRegisteredSession vrs = new ViewRegisteredSession(currentUser, this);
             vrs.setVisible(true);
             this.setVisible(false); 
        });

        logoutButton.addActionListener(e -> Config.backToLogin(this));

        add(panel);
        
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}