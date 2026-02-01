package views;

import controllers.SeminarController;
import controllers.StudentController;
import java.awt.*;
import javax.swing.*;
import models.User;
import utils.Config;

public class RegisterSeminarPage extends JFrame {

    private final User student;
    private final JFrame previousScreen;
    private final StudentController controller;

    private final JTextField titleField;
    private final JTextArea abstractField;
    private final JTextField supervisorField;
    private final JComboBox<String> typeBox;
    private final JComboBox<String> seminarBox;
    private final JTextField txtCloudLink;


    public RegisterSeminarPage(User student, JFrame previousScreen) {
        super("Register Presentation");
        this.student = student;
        this.previousScreen = previousScreen;
        this.controller = new StudentController();

        setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- Fields ---

        // Title
        gbc.gridx = 0; gbc.gridy = 0; add(new JLabel("Research Title:"), gbc);
        titleField = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 0; add(titleField, gbc);

        // Abstract
        gbc.gridx = 0; gbc.gridy = 1; add(new JLabel("Abstract:"), gbc);
        abstractField = new JTextArea(3, 20);
        JScrollPane scrollAbs = new JScrollPane(abstractField);
        gbc.gridx = 1; gbc.gridy = 1; add(scrollAbs, gbc);

        // Supervisor Name
        gbc.gridx = 0; gbc.gridy = 2; add(new JLabel("Supervisor:"), gbc);
        supervisorField = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 2; add(supervisorField, gbc);

        // Type
        gbc.gridx = 0; gbc.gridy = 3; add(new JLabel("Type:"), gbc);
        typeBox = new JComboBox<>(new String[]{"Oral", "Poster"});
        gbc.gridx = 1; gbc.gridy = 3; add(typeBox, gbc);

        // Seminar Dropdown
        gbc.gridx = 0; gbc.gridy = 4; add(new JLabel("Seminar:"), gbc);
        
        // Load seminaar from Controller
        String[] seminars = controller.getAvailableSeminar();
        seminarBox = new JComboBox<>(seminars);
        
        gbc.gridx = 1; gbc.gridy = 4; add(seminarBox, gbc);

        // Cloud Link
        gbc.gridx = 0; gbc.gridy = 5; add(new JLabel("Cloud Share Link:"), gbc);
        txtCloudLink = new JTextField();
        txtCloudLink.setToolTipText("Paste Google Drive or OneDrive link here");
        gbc.gridx = 1; gbc.gridy = 5; add(txtCloudLink, gbc);

        // ----------- Buttons ---------------
        JPanel btnPanel = new JPanel();
        JButton btnSubmit = new JButton("Submit");
        JButton btnCancel = new JButton("Cancel");
        btnPanel.add(btnSubmit);
        btnPanel.add(btnCancel);

        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        add(btnPanel, gbc);

        // ------------ ActionListener -----------

        btnCancel.addActionListener(e -> {
            dispose();
            if (previousScreen != null) previousScreen.setVisible(true);
        });

        btnSubmit.addActionListener(e -> {
            submitPresentation();
        });
        
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void submitPresentation (){ 
        // Validate Input
        if(titleField.getText().isEmpty() || supervisorField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields");
            return;
        }

            String Link = txtCloudLink.getText().toLowerCase();
            boolean isValidLink = Link.contains("drive.google.com") || 
                                  Link.contains("onedrive.live.com") ||
                                  Link.contains("sharepoint.com");
        
        if (!isValidLink) {
            JOptionPane.showMessageDialog(this, "Please provide a valid link.");
            return;
        }

        // Validate Seminar Selection
        String selectedSeminarRaw = (String) seminarBox.getSelectedItem();
        if (selectedSeminarRaw == null) {
            JOptionPane.showMessageDialog(this, "Please select a seminar event.");
            return;
        }

        // Extract IDs
        String seminarID = selectedSeminarRaw.split(" - ")[0].trim(); 
        String studentID = student.getUserID();


        // Update submission.txt
        controller.registerPresentation(
            studentID,
            titleField.getText(),
            (String) typeBox.getSelectedItem(),
            abstractField.getText(),
            supervisorField.getText(),
            seminarID,
            txtCloudLink.getText()
        );

        // Update registration.txt
        SeminarController semC = new SeminarController(); 

        // This boolean tells us if the file write actually happened
        semC.registerStudent(studentID, seminarID);


        // 6. Show User Message
        String msg = "Presentation Submitted Successfully!";

        JOptionPane.showMessageDialog(this, msg);
        dispose();
        if (previousScreen != null) previousScreen.setVisible(true);
        
    }
}