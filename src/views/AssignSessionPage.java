package views;


import controllers.*;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import models.Seminar;
import models.Session;
import models.Submission;
import utils.Config;
import utils.FileHandler;


public class AssignSessionPage extends JFrame{


  private final JComboBox <String> seminarBox; 
  private final JComboBox <String> sessionBox;
  private final JComboBox <String> evaluatorBox;
  private final JComboBox <String> presenterBox;

  public AssignSessionPage() 
  { 
    super("Assign Session");
    //setSize (Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT); 
    
    
    setLayout(new BorderLayout());
    JPanel boxPanel = new JPanel(new GridLayout(0, 2, 10, 20)); 
    JPanel buttonPanel = new JPanel();


    //---------------- Select Seminar Drop Down  --------------------
    JLabel seminarLabel = new JLabel("Select a seminar to assign");
    seminarBox = new JComboBox<>(); 
    loadSeminarList();


    // -------------- Select Session Drop Down ----------------------
    JLabel sessionLabel = new JLabel("Select a session to assign:"); 
    sessionBox = new JComboBox<>();
    loadSessionList();



    //---------------- Select Evaluator Drop Down  -------------------
    JLabel evaluatorLabel = new JLabel("Select assign evaluator: ");
    evaluatorBox = new JComboBox<>(); 
    loadEvaluatorList();

    //---------------- Select Presenter Drop Down  -------------------
    JLabel presenterLabel = new JLabel("Select assign presenter: ");
    presenterBox = new JComboBox<>(); 

    //----------- Button -------------
    JButton saveButton = new JButton("Save"); 
    JButton backButton = new JButton ("Back"); 

    // ------------- Action Listener ------------
    seminarBox.addActionListener(e->{ 
      loadSessionList(); 
    });

    sessionBox.addActionListener(e->{ 
      loadStudentList();
    });

    saveButton.addActionListener(e-> {
      saveToFile();
    });

    backButton.addActionListener(e -> {
      dispose();
      Config.setSeminarDashboardVsible();
    });


    boxPanel.add(seminarLabel); 
    boxPanel.add(seminarBox);

    boxPanel.add(sessionLabel);
    boxPanel.add(sessionBox);

    boxPanel.add(evaluatorLabel);
    boxPanel.add(evaluatorBox); 

    boxPanel.add(presenterLabel);
    boxPanel.add(presenterBox);

    buttonPanel.add(backButton);
    buttonPanel.add(saveButton);
    

    boxPanel.setBorder (BorderFactory.createEmptyBorder(20,20,20,20));


    add(boxPanel, BorderLayout.NORTH);
    add(buttonPanel, BorderLayout.SOUTH);

    pack();

    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
  }


  //function call

  private void loadSeminarList ()
  {
    SeminarController semC = new SeminarController(); 
    List<Seminar> seminarList = semC.getAllSeminars(); 

    for (Seminar seminar : seminarList)
    { 
      seminarBox.addItem(seminar.getSeminarID() + " - "+ seminar.getSeminarTitle());
    }

  }


  private void loadSessionList ()
  { 
    String rawSeminar = (String) seminarBox.getSelectedItem();
    if(rawSeminar == null) return; 
    
    String seminarID = rawSeminar.split(" - ")[0];

    sessionBox.removeAllItems(); 

    SessionController sc = new SessionController(); 
    List<Session> allSessions = sc.getAllSession(); 

    for(Session s : allSessions)
    { 
      if (s.getSeminarID().equals(seminarID))
      {
        sessionBox.addItem(s.getSessionID() + " - " + s.getSessionName() + " - " + s.getSessionType());
      }
    }



  }

  // evaluator and student 
  private void loadEvaluatorList()
  { 
    UserController us = new UserController(); 
    List<String> users = us.getUserRole("Evaluator"); 

    for (String user : users)
    { 
      evaluatorBox.addItem (user);
    }
  }


  private void loadStudentList ()
  { 
    if(seminarBox.getSelectedItem() == null || sessionBox.getSelectedItem() == null)
    {
      return;
    }

    String rawSeminar = (String) seminarBox.getSelectedItem(); 
    String seminarID = rawSeminar.split(" - ")[0].trim();

    
    String rawSession = (String) sessionBox.getSelectedItem();
    String sessionID = rawSession.split(" - ")[0].trim(); 


    //check session type- Oral or Poster
    SessionController sc = new SessionController(); 
    Session selectedSession = sc.getSessionByID(sessionID);

    if (selectedSession == null)
    {
      return;  
    }

    String requiredType = selectedSession.getSessionType().trim(); // poster or oral 

    //get list of registerd student
    SeminarController semC = new SeminarController();
    List<String> registeredStudentIds = semC.getRegisteredStudents(seminarID); 

    if (registeredStudentIds.isEmpty()) {
        presenterBox.removeAllItems();
        return;
    }
  
    SubmissionController subC = new SubmissionController(); 

    List<Submission> allSubmission = subC.getAllSubmissions();

  
    
    presenterBox.removeAllItems(); // clear previous items
    boolean found = false; 


    //loop thru every registered student 
    for (String studentID : registeredStudentIds)
    {         

      // get their submission 
      for (Submission sub : allSubmission)
      {
        // Check if ID matches
        if (sub.getStudentID().trim().equals(studentID)) {
            String studentType = sub.getType().trim();
            
            // COMPARE THE TYPES
            if (studentType.equalsIgnoreCase(requiredType)) {
                presenterBox.addItem(studentID);
                found = true;
            }
        }

      }
    }

    if (!found){ 
      presenterBox.addItem ("No eligible students found!");
    }
  }

  private void saveToFile ()
  {
    //check if all selection have pick smtg 
    if (sessionBox.getSelectedItem() == null || evaluatorBox.getSelectedItem() == null || presenterBox.getSelectedItem() == null)
    { 
      JOptionPane.showMessageDialog(this, "Please select all field!");
      return; 
    }
    

    // retrieve the data from the input of the user from the box
    // need to convert object to string (String)
    String rawSession = (String) sessionBox.getSelectedItem(); 
    String session[] = rawSession.split(" - ");
    String sessionID = session[0]; //abstract the session ID

    String rawEvaluator = (String) evaluatorBox.getSelectedItem();
    String evaluator[] = rawEvaluator.split(" - ");
    String evaluatorID = evaluator[0];// get the evaluator ID

    String rawPresenter = (String) presenterBox.getSelectedItem();
    String presenter[] = rawPresenter.split(" - ");
    String presenterID = presenter[0];// get the presenter ID

    SessionController sc = new SessionController(); 
    Session targetedSession = sc.getSessionByID(sessionID); 


    // get boardID in the submission.txt 
    String boardIdToSave = "-";
    SubmissionController sb = new SubmissionController();
    List <Submission> allSub = sb.getAllSubmissions();
    
    for(Submission sub : allSub)
    { 
      if (sub.getStudentID().equals(presenterID))
      { 
        boardIdToSave = sub.getBoardID(); // put the boardID we generate in submission into session
        break;
      }
    }


    if (targetedSession != null)
    { 
      // update the assigned presenter and evaluator to the database
      targetedSession.setEvaluator(evaluatorID);
      targetedSession.setPresenter(presenterID);
      targetedSession.setBoardID(boardIdToSave);

      //put all the object into string
      String line = targetedSession.toFileLine(); 
      //update 
      FileHandler.updateData(Config.SESSIONS_FILE, sessionID, line);
      
      JOptionPane.showMessageDialog(this, "Session Assign Successful!");
    }
  }
}


