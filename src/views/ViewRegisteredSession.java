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
    
        setSize(Config.WINDOW_WIDTH + 300, Config.WINDOW_HEIGHT / 2);
        setLayout(new BorderLayout());
    
        displayRegisteredSessionTable();
    
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            previousScreen.setVisible(true);
            this.dispose();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);
    
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
    
    private void displayRegisteredSessionTable() {
        // Get seminars 
        List<Seminar> registeredSeminars = controller.getStudentRegisteredSeminars(student.getUserID());
        
        // table 
        String[] columnNames = {"Session ID", "Seminar ID", "Seminar Name", "Venue", "Date", "Time", "Eva Id"};
        tableModel = new DefaultTableModel(columnNames, 0);

        // get student's registered to seminar only
        for (Seminar seminar : registeredSeminars) {

            String[] sessionInfo = getSessionInfoBySeminarID(seminar.getSeminarID(), student.getUserID());

            String venue = sessionInfo[0];
            String time = sessionInfo[1];
            String sessionID = sessionInfo[2];
            String evaID = sessionInfo[3];
            
            tableModel.addRow(new Object[]{
                sessionID,
                seminar.getSeminarID(),
                seminar.getSeminarTitle(),
                venue,
                seminar.getSeminarDate(),
                time,
                evaID
            });
        }
        
        sessionTable = new JTable(tableModel);
        sessionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(sessionTable);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private String[] getSessionInfoBySeminarID(String seminarID, String studentID) {
        String[] info = {"N/A", "N/A", "N/A", "N/A"};  // {venue, time, sessionID, evaID}
        
        try {
            List<String> sessions = FileHandler.readAllLines("data/sessions.txt"); 
            
            for (String session : sessions) {
                String[] parts = session.split("\\|"); 
                
                // Format: SESSION-ID|SEMINAR-ID|Title|Date|Time|VENUE|Type|Evaluator|Student
                if (parts.length >= 9) {
                    String currentSeminarID = parts[1].trim();
                    String currentStudentID = parts[8].trim();

                    // FIX: Check BOTH SeminarID AND StudentID
                    if (currentSeminarID.equals(seminarID) && currentStudentID.equals(studentID)) {
                        info[0] = parts[5].trim();  // Venue at index 5
                        info[1] = parts[4].trim();  // Time at index 4
                        info[2] = parts[0].trim();  // Session ID at index 0
                        info[3] = parts[7].trim();  // Evaluator ID at index 7
                        return info; 
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return info;
    }
}
