package views;


import controllers.SeminarController;
import controllers.SessionController;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import models.Seminar;
import models.Session;
import utils.Config;


public class SeminarDashboard extends JFrame {

  private JComboBox<String> seminarBox;
  private JButton backButton;
  private JButton createNewSessionButton;
  private JButton assignSessionButton;
  private DefaultTableModel tableModel;
  private String currentSeminarID = null;


  public SeminarDashboard() { 
    super("Seminar Dashboard"); 
    setSize (Config.WINDOW_WIDTH + 200, Config.WINDOW_HEIGHT/2);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout());

    displayAllSessionTable();
    displaySeminarOption();
    
    addButton();

    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
  }


 
  private void loadSeminar() { 

    SeminarController sc = new SeminarController();
    List <Seminar> seminarList = sc.getAllSeminars(); 

    for (Seminar s : seminarList)
    {
      seminarBox.addItem(s.getSeminarID() + " - " + s.getSeminarTitle());
    }
  }

  private void displaySeminarOption() { 

      //Top panel display option of seminar
      JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
      topPanel.add (new JLabel("Select Seminar Event: "));
      seminarBox = new JComboBox<>();
      loadSeminar();

      seminarBox.addActionListener(e-> {
        updateSessionTable();
      });

      if (seminarBox.getItemCount() > 0)
      {
        seminarBox.setSelectedIndex(0);
      }

      topPanel.add(seminarBox);
      add(topPanel, BorderLayout.NORTH);

      updateSessionTable();
    }


  private void updateSessionTable() { 
    
    tableModel.setRowCount(0);
    
    String selectedSeminar = (String) seminarBox.getSelectedItem(); 
    if (selectedSeminar == null)
    { 
      currentSeminarID = null; 
      return;
    }

    currentSeminarID = selectedSeminar.split(" - ")[0];
    SessionController sessionController = new SessionController(); 
    List <Session> allSession = sessionController.getAllSession(); 

    for (Session s : allSession)
    { 
      if (s.getSeminarID().equals(currentSeminarID))
      {
        String [] rowSessionData = {
        s.getSessionID(),
        s.getSessionName(),
        s.getSessionDate(),
        s.getSessionTime(),
        s.getSessionVenue(),
        s.getSessionType(),
        s.getEvaluator(),
        s.getPresenter()
        };
        tableModel.addRow (rowSessionData);
      }
    }
  }

  private void displayAllSessionTable() {
    
    // table column name 
    String [] titles = {"Session ID", "Session Name", "Date", "Time", "Venue", "Type", "Evaluator", "Presenter"};

    // init table model
    tableModel = new DefaultTableModel(titles, 0); // 0 means no row initially
   
    // display table 
    JTable sessionTable = new JTable(tableModel); 
    sessionTable.setRowHeight(15);

    JScrollPane scrollPane = new JScrollPane(sessionTable);
    add(scrollPane, BorderLayout.CENTER);

    
  }

  private void addButton () { 
    
    JPanel buttonPanel = new JPanel();

    //back button
    backButton = new JButton("Back");

    //create new sesssion button 
    createNewSessionButton = new JButton("Create New Session");

    //assign session button
    assignSessionButton = new JButton ("Assign Session");

    buttonPanel.add(backButton);
    buttonPanel.add(createNewSessionButton);
    buttonPanel.add(assignSessionButton);
    
    add (buttonPanel, BorderLayout.SOUTH); 

    //--------------------------- Action listeners ----------------------------
    createNewSessionButton.addActionListener(e -> {

      if(currentSeminarID == null)
      {
        return;
      }
      

      CreateSessionPage CS = new CreateSessionPage(this, currentSeminarID);
      CS.setVisible(true);
      this.setVisible(false);
    });
    
    assignSessionButton.addActionListener(e->{ 

      AssignSessionPage AS = new AssignSessionPage();
      AS.setVisible(true);
      this.setVisible(false);

    });

    backButton.addActionListener(e-> {
      dispose();
      Config.setCoordinatorDashboardVsible();
    });
    

  }




}



