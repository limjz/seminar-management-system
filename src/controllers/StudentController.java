package controllers;

import java.util.ArrayList;
import java.util.List;
import models.Seminar;
import models.Session;
import models.Submission;
import utils.Config;
import utils.FileHandler;

public class StudentController {

    public String[] getAvailableSession() {
        SessionController sc = new SessionController();
        List<Session> allSessions = sc.getAllSession(); 
        
        List<String> displayList = new ArrayList<>();
        
        for (Session s : allSessions) {
            displayList.add(s.getSeminarID() + " | " + s.getSessionName());
        }
        
        return displayList.toArray(new String[0]);
    }

    public String[] getAvailableSeminar(){ 
        SeminarController sc = new SeminarController();
        List<Seminar> allSeminars = sc.getAllSeminars(); 

        List<String> displayList = new ArrayList<>(); 

        for (Seminar s : allSeminars)
        { 
            displayList.add(s.getSeminarID() + " - " + s.getSeminarTitle()); 
        }
        return displayList.toArray(new String [0]);

    }


    // Register Presentation
    public boolean registerPresentation(String studentID, String title, String type, String abstractText, String supervisor, String sessionInfo, String cloudLink) {
        
        int count = FileHandler.readAllLines(Config.SUBMISSIONS_FILE).size();
        int nextNum = count + 1;

        String subID = "SUB-00" + nextNum;
        String boardID = "-";

        String seminarID = sessionInfo.split(Config.DELIMITER_READ)[0];

        String finalMaterial = (cloudLink == null) ? "null" : cloudLink;
        
        if (type.equalsIgnoreCase("Poster"))
        { 
            boardID = generateNextBoardID();
        }


        // Create Object
        Submission newPres = new Submission(
            subID,
            studentID,
            title,
            abstractText,
            supervisor,
            type,
            boardID,
            seminarID,
            finalMaterial
        );

        FileHandler.appendData(Config.SUBMISSIONS_FILE, newPres.toFileLine());
        return true;
        }   

    private String generateNextBoardID (){ 
        
        SubmissionController sc = new SubmissionController();
        List<Submission> allSub = sc.getAllSubmissions(); 

        int poster_count = 0;
        for (Submission s : allSub)
        { 
            if ("Poster".equalsIgnoreCase(s.getType()))
            { 
                poster_count ++; 
            }
        }
        
        return String.format("B-%02d", poster_count + 1);
    }
        
    public List<Seminar> getStudentRegisteredSeminars(String studentId) {
        List<Seminar> registeredSeminars = new ArrayList<>();
        
        try {
            // Read all registrations from registration.txt
            List<String> registrations = FileHandler.readAllLines("data/registration.txt");
            
            // Get all seminars
            SeminarController seminarController = new SeminarController();
            List<Seminar> allSeminars = seminarController.getAllSeminars();
            
            // Filter seminars where student is registered
            for (String registration : registrations) {
                String[] parts = registration.split("\\|");
                if (parts.length >= 2 && parts[1].trim().equals(studentId)) {
                    String seminarId = parts[0].trim();
                    
                    // Find matching seminar
                    for (Seminar seminar : allSeminars) {
                        if (seminar.getSeminarID().equals(seminarId)) {
                            registeredSeminars.add(seminar);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return registeredSeminars;
    }
            
    }
