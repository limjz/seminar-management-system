package controllers;

import java.util.*;
import models.Submission;
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

  public List<User> getAllUser () 
  { 
    List<User> userList = new ArrayList<>(); 
    List<String> allLines = FileHandler.readAllLines(Config.USER_FILE);
    for (String lines : allLines)
    { 
      User u = User.fromFileLine(lines); 
      if (u!= null)
      { 
        userList.add(u);
      }
    }
    return userList;
  }

  public List<String> getStudentBySubmissionType (String targetType) 
  { 
    List<User> allUser = getAllUser(); 
    List<String> filteredStudentList = new ArrayList<>();

    List<Submission> allSub = new SubmissionController().getAllSubmission();

    for (User u : allUser)
    { 
      if (!u.getUserRole().equals("Student"))
        continue;

      for (Submission s : allSub)
      {
        if (u.getUserID().equals(s.getStudentId()) && s.getType().equals(targetType))
        { 
          filteredStudentList.add(u.getUserID() + " - " + u.getUserName());
          break;
        }
      }
      
    }
    return filteredStudentList;
  }

}
