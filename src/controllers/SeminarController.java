package controllers;

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
    List<String> allRegistrations = FileHandler.readAllLines(Config.SEMIMAR_REGISTRATION_FILE); 
    
    for (String line : allRegistrations)
    { 
      String [] parts = line.split(Config.DELIMITER_READ); 
      
      String registeredSeminarID = parts[0]; 
      String registerdStudentID = parts [1];

      if (registerdStudentID.equals(studentID) && registeredSeminarID.equals(seminarID))
      { 
        return true;
      } 
    }

    return false; // loop thru the registration.txt file and didnt found the name

  }

  //student register and append to5 registration.txt
  public boolean registerStudent (String studentID, String seminarID)
  { 
    if (isStudentRegistered(studentID, seminarID))
    { 
      return false; //already registered
    }

    String line = seminarID + " - " + studentID;
    FileHandler.appendData(Config.SEMIMAR_REGISTRATION_FILE, line);
    return true;
  }

  public List<String> getRegisteredStudents (String seminarID){ 
    List<String> studentIDs = new ArrayList<>();
    List<String> allRegistered = FileHandler.readAllLines(Config.SEMIMAR_REGISTRATION_FILE);
    
    for (String line :allRegistered)
    { 
      String[] parts = line.split(Config.DELIMITER_READ); 
      if (parts[0].equals(seminarID))
      { 
        studentIDs.add(parts[0]);
      }
    }

    return studentIDs;
  }


}
