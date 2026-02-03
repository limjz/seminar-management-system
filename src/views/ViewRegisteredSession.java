package views;

import controllers.SeminarController;
import controllers.StudentController;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import models.Seminar;
import models.User;
import utils.Config;
import utils.FileHandler;

public class ViewRegisteredSession extends JFrame {
    private final User student;
    private final JFrame previousScreen;
    private final StudentController controller;
    private final SeminarController seminarController;
    
    private DefaultTableModel tableModel;
    private JTable sessionTable;
    
    public ViewRegisteredSession(User student, JFrame previousScreen) {
        super("View Registered Sessions");
        this.student = student;
        this.previousScreen = previousScreen;
        this.controller = new StudentController();
        this.seminarController = new SeminarController();
    
        setSize(Config.WINDOW_WIDTH + 200, Config.WINDOW_HEIGHT / 2);
        setLayout(new BorderLayout());
    
        displayRegisteredSessionTable();
    
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            previousScreen.setVisible(true);
            this.dispose();
        });
        add(backButton, BorderLayout.SOUTH);
    
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
    
    private void displayRegisteredSessionTable() {
        // Get seminars the student is registered to
        List<Seminar> registeredSeminars = controller.getStudentRegisteredSeminars(student.getUserID());
        
        // Create table model with columns - added "Time"
        tableModel = new DefaultTableModel(new String[]{"Seminar ID", "Seminar Name", "Venue", "Date", "Time"}, 0);
        
        // Populate table with only registered seminars
        for (Seminar seminar : registeredSeminars) {
            // Get venue and time from sessions.txt for this seminar
            String[] sessionInfo = getSessionInfoBySeminarID(seminar.getSeminarID());
            String venue = sessionInfo[0];
            String time = sessionInfo[1];
            
            tableModel.addRow(new Object[]{
                seminar.getSeminarID(),
                seminar.getSeminarTitle(),
                venue,
                seminar.getSeminarDate(),
                time
            });
        }
        
        sessionTable = new JTable(tableModel);
        sessionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(sessionTable);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private String[] getSessionInfoBySeminarID(String seminarID) {
        String[] info = {"N/A", "N/A"};  // {venue, time}
        
        try {
            // FIX: Called statically instead of creating an object
            // Consider changing "data/sessions.txt" to Config.SESSIONS_FILE if it exists
            List<String> sessions = FileHandler.readAllLines("data/sessions.txt"); 
            
            for (String session : sessions) {
                // Using the Config delimiter just in case the separator changes later
                String[] parts = session.split("\\|"); 
                
                // Format: SESSION-ID|SEMINAR-ID|Title|Date|Time|VENUE|Type|Evaluator|Student
                if (parts.length >= 6 && parts[1].trim().equals(seminarID)) {
                    info[0] = parts[5].trim();  // Venue at index 5
                    info[1] = parts[4].trim();  // Time at index 4
                    return info;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return info;
    }
}
