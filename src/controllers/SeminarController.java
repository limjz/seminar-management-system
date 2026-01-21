package controllers;

import java.io.File;
import java.util.*; 
import models.Seminar;
import utils.Config;
import utils.FileHandler;


public class SeminarController {

  public static boolean createSeminar(String title, String date ) {
    // Auto generate seminarID based on number of existing sessions
    int newSeminarID = FileHandler.readAllLines(Config.SEMINARS_FILE).size() + 1;
    String seminarID = "SESSION-0" + newSeminarID; //SESSION - 01, ...

    //create new session object and append to database

    Seminar newSeminar = new Seminar(seminarID, title, date);
    FileHandler.appendData(Config.SEMINARS_FILE, newSeminar.toFileLine());

    return true;
  }

  public List<Seminar> getAllSeminars() { 
    List<Seminar> seminarList = new ArrayList<>(); 
    List<String> allLines = FileHandler.readAllLines(Config.SEMINARS_FILE);

    for (String line : allLines){ 
      Seminar s = Seminar.fromFileLine(line);
      if (s != null){ 
        seminarList.add(s); 
      }
    }
    return seminarList;
  }


  // check registration.txt if the student is registered to any seminar
  private boolean isStudentRegistered (String studentID, String seminarID)
  { 
    File f = new File(Config.SEMINAR_REGISTRATION_FILE);
    if (!f.exists()) return false;

    List<String> lines = FileHandler.readAllLines(Config.SEMINAR_REGISTRATION_FILE);
    
    for (String line : lines) {
        // Trim to remove accidental spaces at ends
        String cleanLine = line.trim();
        
        // Skip empty lines 
        if (cleanLine.isEmpty()) continue;

        String[] parts = cleanLine.split("\\|");

        
        // Check if parts.length >= 2 BEFORE trying to read parts[1]
        if (parts.length >= 2) {
            String currentSeminarId = parts[0].trim();
            String currentStudentId = parts[1].trim();
            
            if (currentSeminarId.equals(seminarID) && currentStudentId.equals(studentID)) {
                return true;
            }
        }
    }
    return false;

  }

  //student register and append to registration.txt
  public boolean registerStudent (String studentID, String seminarID)
  { 

    // 1. Check if IDs are valid
    if (studentID == null || seminarID == null || studentID.isEmpty() || seminarID.isEmpty()) {
        return false;
    }

    if (isStudentRegistered(studentID, seminarID))
    { 
      return false; //already registered
    }

    String line = seminarID + "|" + studentID;
    FileHandler.appendData(Config.SEMINAR_REGISTRATION_FILE, line);


    return true;
  }

  public List<String> getRegisteredStudents (String seminarID){ 
    List<String> studentIDs = new ArrayList<>();
    List<String> allRegistered = FileHandler.readAllLines(Config.SEMINAR_REGISTRATION_FILE);
    
    for (String line :allRegistered)
    { 
      String[] parts = line.split(Config.DELIMITER_READ); 
      if (parts[0].equals(seminarID))
      { 
        studentIDs.add(parts[1]);
      }
    }

    return studentIDs;
  }


}
