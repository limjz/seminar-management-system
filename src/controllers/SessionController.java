package controllers;

import java.util.*;
import models.Session;
import utils.Config;
import utils.FileHandler;


public class SessionController {

    public static boolean createSession(String seminarID, String sessionName, String sessionDate, String sessionTime, String sessionVenue, String sessionType) {
    
    // Auto generate sessionID based on number of existing sessions
    int newSessionID = FileHandler.readAllLines(Config.SESSIONS_FILE).size() + 1;
    String sessionID = "SESSION-0" + newSessionID; //SESSION - 01, ...

    //create new session object and append to database
    Session newSession = new Session(sessionID, seminarID, sessionName, sessionDate, sessionTime, sessionVenue, sessionType, "-", "-");
    FileHandler.appendData(Config.SESSIONS_FILE, newSession.toFileLine());


    return true;
  }



  public List<Session> getAllSession ()
  { 
    List<Session> sessionList = new ArrayList<> ();
    List<String> allLines = FileHandler.readAllLines(Config.SESSIONS_FILE);
    
    for (String lines : allLines)
    { 
      Session s = Session.fromFileLine(lines);
      if (s != null) // if read smtg then add to the list
      { 
        sessionList.add(s);
      }
    }

    return sessionList;
  }

  // search ID can know which data 
  public Session getSessionByID (String id)
  {
    List<Session> allSession = getAllSession(); 

    for (Session s : allSession)
    { 
      if(s.getSessionID().equals(id))
        return s; // here s is direct to one line of data 
    }

    return null;
  }


}
