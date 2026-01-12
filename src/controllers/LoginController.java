package controllers;

import java.util.*;
import models.User;
import utils.Config;
import utils.FileHandler;

public class LoginController {
  
  public static User authentication(String id, String pwd)
  { 
    //read data from the database and validate
    List <String> lines = FileHandler.readAllLines(Config.USER_FILE);

    for (String line : lines) // check all the lines 
    {
      User user_data = User.fromFileLine(line);
      
      if (user_data == null) continue; 

      if (user_data.getUserID().equals(id))
      {
        if (user_data.getUserPwd().equals(pwd))
        {
          char first_alp = id.charAt(0); // get the first letter of the ID to indicate the role

          switch (first_alp)
          {
            case 'C':
              user_data.setUserRole("Coordinator");
              break; 
            case 'S': 
              user_data.setUserRole("Student"); 
              break;
            case 'E': 
              user_data.setUserRole("Evaluator");  
              break;
          }

          return user_data;
        }
      }

    }
    // if not found
    return null;
  }


}
