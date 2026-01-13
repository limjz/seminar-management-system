package controllers;

import java.util.*;
import models.Session;
import utils.Config;
import utils.FileHandler;


public class SessionController {

  public List<Session> getAllSession ()
  { 
    List<Session> sessionList = new ArrayList<> ();
    List<String> allLines = FileHandler.readAllLines(Config.SESSIONS_FILE);
    
    for (String lines : allLines)
    { 
      Session s = Session.fromFileLine(lines);
      if (s != null)
      { 
        sessionList.add(s);
      }
    }

    return sessionList;
  }


  public Session getSessionByID (String id)
  {
    List<Session> allSession = getAllSession(); 

    for (Session s : allSession)
    { 
      if(s.getSessionID().equals(id))
        return s;
    }

    return null;
  }

  public Session updateSession () 
  { 


    
    return null; 
  }

}
