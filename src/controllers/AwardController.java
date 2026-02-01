package controllers;

import java.util.List;
import models.Evaluation;
import models.Submission;
import models.Winner;

public class AwardController {

  //Only for POSTER and ORAL, target in one category instead of in whole 
  public Winner getBestPresenter (String seminarID, String type){ 
    
    EvaluationController ec = new EvaluationController(); 
    SubmissionController sc = new SubmissionController();


    List<Evaluation> allEvas = ec.getAllEvaluaations();
    List<Submission> allSubs = sc.getAllSubmissions(); 

    Winner best = null ;
    int highestScore = -1; //empty
      

    for (Evaluation ev : allEvas)
    {
      if (!ev.getSeminarId().equals(seminarID)) continue ; // filter by seminar 
      
      String subType = getSubmissionType(ev.getSubmissionId(), allSubs); //helper function at bottom 

      if (subType.equalsIgnoreCase(type))
      { 
        if (ev.getTotalScore() > highestScore){ 
          highestScore = ev.getTotalScore(); 
          int presentationScore = ev.getScore4();
          String title = getSubmissionTitle(ev.getSubmissionId(), allSubs);
          
          best  = new Winner (
            ev.getStudentId(), 
            title, 
            highestScore, // total 
            presentationScore // presentation // for people's choice award 
          );

        }
      }

    }
    return best; //include the winner details 
  }


  //filter the whole 
  public Winner getPeopleChoice (String seminarID){ 

    EvaluationController ec = new EvaluationController(); 
    SubmissionController sc = new SubmissionController();


    List<Evaluation> allEvas = ec.getAllEvaluaations();
    List<Submission> allSubs = sc.getAllSubmissions(); 

    Winner best = null; 

    // need two score for tie break if same score for presentationScore, 
    // then will take the highest total score as People's Choice 
    int bestPresScore = -1; //empty
    int bestTotalScore = -1; //empty

    for (Evaluation ev : allEvas)
    { 
      if (!ev.getSeminarId().equals(seminarID)) continue; 

      int currentPresScore = ev.getScore4(); 
      int currentTotalScore = ev.getTotalScore();

      if (currentPresScore > bestPresScore) //check for the higher presentationScore first 
      { 
        bestPresScore = currentPresScore; 
        bestTotalScore = currentTotalScore; 

        String title = getSubmissionTitle(ev.getSubmissionId(), allSubs); 
        best = new Winner ( 
          ev.getStudentId(),
          title, 
          currentTotalScore, 
          currentPresScore
        );

      }
      else if (currentPresScore == bestPresScore) // two or more winner, meaning the score4 all is 5/5
      { 
        if (currentTotalScore > bestTotalScore) // check the highest total score 
        { 
          bestTotalScore = currentTotalScore; 
          String title = getSubmissionTitle(ev.getSubmissionId(), allSubs);

          best = new Winner ( 
            ev.getStudentId(), 
            title, 
            currentTotalScore, 
            currentPresScore
          );

        }
      }

    }
    return best;
  } 

  private String getSubmissionType (String subID, List<Submission> subs){ 

    for (Submission s : subs)
    { 
      if ( s.getSubmissionID().equals(subID)) 
      { 
        return s.getType();
      }
    }

    return "Unknown_type";
  }


  private String getSubmissionTitle (String subID, List<Submission> subs){ 

      for (Submission s : subs)
      { 
        if (s.getSubmissionID().equals(subID)) 
        { 
          return s.getTitle();
        }
      }

      return "No Title";
    }
}
