package controllers;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List; 
import models.Session;
import utils.*;

public class ReportController {

  public String getScheduleTextArea (String seminarID, String seminarTitle)
  { 
    StringBuilder sb = new StringBuilder(); 

    sb.append (" =================================================== \n"); 
    sb.append (" | Seminar Schedule: ").append (seminarID).append (" - ").append(seminarTitle).append(" | \n");
    sb.append (" =================================================== \n"); 

    // get session 
    SessionController sc = new SessionController(); 
    List<Session> allSessions = sc.getAllSession(); 
    List<Session> seminarSession = new ArrayList<>();


    for (Session s : allSessions)
    { 
      if (s.getSeminarID().equals(seminarID))
      { 
        seminarSession.add(s);
      }
    } 
    if (seminarSession.isEmpty())
    { 
      return "No session scheduled in this seminar"; 
    }

    SimpleDateFormat parser = new SimpleDateFormat("hh:mm a"); 
    seminarSession.sort((s1, s2) ->{ 
      try {
          Date d1 = (Date) parser.parse (s1.getSessionTime());
          Date d2 = (Date) parser.parse (s2.getSessionTime());
          return d1.compareTo(d2); 
      } 
      catch (Exception e) {
        return 0;
      }
    });


    for (Session s : seminarSession)
    { 
      sb.append (String. format("[ %s ] %s\n", s.getSessionTime(), s.getSessionName())); 
      sb.append("   Venue:     ").append(s.getSessionVenue()).append("\n");
      sb.append("   Presenter: ").append(s.getPresenter()).append("\n");
      sb.append("--------------------------------------------------\n");
    }


    return sb.toString();
  }
  

  public String getEvalutionReportTextArea (String seminarID, String seminarTitle)
  { 
    StringBuilder sb = new StringBuilder(); 

    sb.append (" =================================================== \n"); 
    sb.append (" | Final Evaluation Report: ").append (seminarID).append (" - ").append(seminarTitle).append(" | \n");
    sb.append (" =================================================== \n"); 

    List<String> evaluationLines = FileHandler.readAllLines(Config.EVALUATIONS_FILE); 
    SessionController sc = new SessionController(); 
    List<Session> allSessions = sc.getAllSession(); 

    int passed = 0; 
    int failed = 0; 
    int total_presenter = 0;


    for (Session s : allSessions)
    { 
      if (s.getSeminarID().equals(seminarID))
      { 
        if (s.getPresenter().equals("-") || s.getPresenter().isEmpty())
        { 
          continue; // skip the empty one 
        }

        String studentID = s.getPresenter(); 
        String score = " - ";
        String comment = " - ";  
        
        for (String line : evaluationLines)
        { 
          String [] parts = line.split(Config.DELIMITER_READ); 
          if (parts.length >= 9 && parts[2].equals(studentID))
          {
            score = parts[8]; //total score in the evaluation.txt, last object in the list
            comment = parts[7];

            int numScore = Integer.parseInt(score); 
            if (numScore > 10)
            { 
              passed ++;
            }
            else 
            { 
              failed ++;
            }
            total_presenter ++;

            break;
          }

        }


        sb.append("Student: ").append(studentID).append("\n");
        sb.append("   Session:  ").append(s.getSessionName()).append("\n");
        sb.append("   Score:    ").append(score).append("\n");
        sb.append("   Comments: ").append(comment).append("\n");
        sb.append("----------------------------------------\n");
      }

        
    }

    sb.append("\nSUMMARY STATISTICS:\n");
    sb.append("Total Graded: ").append(total_presenter).append("\n");
    sb.append("Passed:       ").append(passed).append("\n");
    sb.append("Failed:       ").append(failed).append("\n");

    return sb.toString();


  }






}
