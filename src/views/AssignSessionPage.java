package views;


import controllers.*;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import models.Session;
import utils.Config;
import utils.FileHandler;


public class AssignSessionPage extends JFrame{

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



    // -------------- Session Selection ----------------------
    JLabel sessionLabel = new JLabel("Select a session to assign:"); 
    sessionBox = new JComboBox<>();
    loadSessionListfromFile();


    //---------------- Evaluator Selection -------------------
    JLabel evaluatorLabel = new JLabel("Select assign evaluator: ");
    evaluatorBox = new JComboBox<>(); 
    loadUserList(evaluatorBox, "Evaluator");

    //---------------- Presenter Selection -------------------
    JLabel presenterLabel = new JLabel("Select assign presenter: ");
    presenterBox = new JComboBox<>(); 
    loadUserList(presenterBox, "Student");

    // save button 
    JButton saveButton = new JButton("Save"); 
    saveButton.addActionListener(e-> {
      saveToFile();
    });
    
    // Back button
    JButton backButton = new JButton ("Back"); 
    backButton.addActionListener(e -> {
      dispose();
      Config.setCoordinatorDashboardVsible();
    });

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
  private void loadSessionListfromFile()
  { 
    SessionController sc = new SessionController(); 
    List<Session> sessionList = sc.getAllSession(); 
    
    for (Session session : sessionList)
    {
      sessionBox.addItem(session.getSessionID() + " - " + session.getSessionVenue());
    }
    
  }


  private void loadUserList(JComboBox<String> box, String role)
  { 
    UserController us = new UserController(); 
    List<String> users = us.getUserRole(role); 

    for (String user : users)
    { 
      box.addItem (user);
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

    if (targetedSession != null)
    { 
      // update the assigned presenter and evaluator to the database
      targetedSession.setEvaluator(evaluatorID);
      targetedSession.setPresenter(presenterID);

      //put all the object into string
      String line = targetedSession.toFileLine(); 
      //update 
      FileHandler.updateData(Config.SESSIONS_FILE, sessionID, line);
      
      JOptionPane.showMessageDialog(this, "Session Assign Successful!");
    }
  }
}


