package controllers;


import java.util.*;
import models.Submission;
import utils.Config;
import utils.FileHandler;


public class SubmissionController {


  public List<Submission> getAllSubmissions ()
  {
    List<Submission> submissionsList = new ArrayList<> (); 
    List<String> allLines = FileHandler.readAllLines(Config.SUBMISSIONS_FILE);

    for (String lines : allLines)
    { 
      Submission sub = Submission.fromFileLine(lines);
      if (sub != null) // if read smtg then add to the list
      { 
        submissionsList.add(sub);
      }
    }

    return submissionsList;
  } 



  // public List<String> getPresentationsSeminar (String seminarID){ 
  //   List<Presentation> allPresentations = getAllPresentations(); 
  //   List<String> filterStudents = new ArrayList<>(); 

  //   for (Presentation p : allPresentations)
  //   { 
  //     if (p.getSeminarID().equals(seminarID))
  //     {
  //         filterStudents.add(p.getPresID() + " - " + p.getType());
  //     }
  //   }
  //     return filterStudents;
  // }

}
