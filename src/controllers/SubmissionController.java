package controllers;


import java.util.*;
import models.Submission;
import utils.Config;
import utils.FileHandler;


public class SubmissionController {


  public List<Submission> getAllSubmission ()
  {
    List<Submission> submissionList = new ArrayList<> (); 
    List<String> allLines = FileHandler.readAllLines(Config.SUBMISSIONS_FILE);

    for (String lines : allLines)
    { 
      Submission sub = Submission.fromFileLine(lines);
      if (sub != null) // if read smtg then add to the list
      { 
        submissionList.add(sub);
      }
    }

    return submissionList;
  } 
}
