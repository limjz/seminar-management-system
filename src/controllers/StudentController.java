package controllers;

import java.util.ArrayList;
import java.util.List;
import models.Seminar;
import models.Session;
import models.Submission;
import utils.Config;
import utils.FileHandler;

public class StudentController {

    // Get Available Sessions 
    // Uses your existing SessionController to get objects cleanly
    public String[] getAvailableSession() {
        SessionController sc = new SessionController();
        List<Session> allSessions = sc.getAllSession(); // From your SessionController.java
        
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
    public boolean registerPresentation(String studentID, String title, String type, String abstractText, String supervisor, String sessionInfo, String filePath) {
        
        int count = FileHandler.readAllLines(Config.SUBMISSIONS_FILE).size();
        int nextNum = count + 1;

        String subID = "SUB-00" + nextNum;

        String seminarID = sessionInfo.split(" \\| ")[0];

        String finalPath = (filePath == null) ? "null" : filePath;

        // Create Object
        Submission newPres = new Submission(
            subID,
            studentID,
            title,
            abstractText,
            supervisor,
            type,
            seminarID,
            finalPath
        );

        FileHandler.appendData(Config.SUBMISSIONS_FILE, newPres.toFileLine());
        return true;
        }   
    }