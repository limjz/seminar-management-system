package controllers;

import java.util.*;
import models.User;
import utils.Config;
import utils.FileHandler;


public class UserController {
  
  public static List<String> getUserRole (String targetRole)
  { 
    List<String> result = new ArrayList<>();
    List<String> allLines = FileHandler.readAllLines(Config.USER_FILE); 
    
    for (String lines:allLines)
    { 
      User user = User.fromFileLine(lines); 
      if (user == null ) continue;

      if (user.getUserRole().equals(targetRole))
      { 
        result.add (user.getUserID() + " - " + user.getUserName()); 
      }
    }
    
    return result; 
  }



}
