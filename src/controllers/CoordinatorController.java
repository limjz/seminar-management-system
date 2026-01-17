package controller;

import model.Session;
import utils.Config;
import utils.FileHandler;


public class CoordinatorController {
  
  public static boolean createSession(String sessionName, String sessionDate, String sessionTime, String sessionVenue, String sessionType) {
    
    // Auto generate sessionID based on number of existing sessions
    int newSessionID = FileHandler.readAllLines(Config.SESSIONS_FILE).size() + 1;
    String sessionID = "SESSION-0" + newSessionID; //SESSION - 01, ...

    //create new session object and append to database
    Session newSession = new Session(sessionID, sessionName, sessionDate, sessionTime, sessionVenue, sessionType);
    FileHandler.appendData(Config.SESSIONS_FILE, newSession.toFileLine());


    return true;
  }
}
