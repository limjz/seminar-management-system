package controllers;

import java.util.ArrayList;
import java.util.List;
import models.Presentation;
import models.Session;
import utils.Config;
import utils.FileHandler;

public class StudentController {

    // 1. Get Available Sessions 
    // Uses your existing SessionController to get objects cleanly
    public String[] getAvailableSession() {
        SessionController sc = new SessionController();
        List<Session> allSessions = sc.getAllSession(); // From your SessionController.java
        
        List<String> displayList = new ArrayList<>();
        
        for (Session s : allSessions) {
            displayList.add(s.getSessionID() + " | " + s.getSessionName());
        }
        
        return displayList.toArray(new String[0]);
    }

    // 2. Register Presentation
    public boolean registerPresentation(String studentID, String title, String type, String abstractText, String supervisor, String sessionInfo, String filePath) {
        
        int count = FileHandler.readAllLines(Config.PRESENTATION_FILE).size();
        int nextNum = count + 1;

        String presID = "PresentationID-0" + nextNum;

        String sessionID = sessionInfo.split(" \\| ")[0];

        String finalPath = (filePath == null) ? "null" : filePath;

        // Create Object
        Presentation newPres = new Presentation(
            presID,
            studentID,
            title,
            abstractText,
            supervisor,
            type,
            sessionID,
            finalPath
        );

        FileHandler.appendData(Config.PRESENTATION_FILE, newPres.toFileLine());
        return true;
        }   
    }