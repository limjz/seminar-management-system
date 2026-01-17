package views;

import controllers.SessionController;
import java.awt.BorderLayout;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import models.*;
import utils.Config;


public class ViewSessionPage extends JFrame {

  public ViewSessionPage(JFrame previousScreen) {
    super("All Session Page"); 
    setSize (Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout());

    // table column name 
    String [] titles = {"Session ID", "Session Name", "Date", "Time", "Venue", "Type", "Evaluator", "Presenter"};

    // init table model
    DefaultTableModel model = new DefaultTableModel(titles, 0); // 0 means no row initially
    SessionController sc = new SessionController(); 
    List<Session> allSession = sc.getAllSession();

    //load all data from session.txt to table
    if (allSession != null){
      for (Session session : allSession)
    { 
      String [] rowSessionData = {
        session.getSessionID(),
        session.getSessionName(),
        session.getSessionDate(),
        session.getSessionTime(),
        session.getSessionVenue(),
        session.getSessionType(),
        session.getEvaluator(),
        session.getPresenter()
      };

      model.addRow(rowSessionData);
      }
    }

    // display table 
    JTable sessionTable = new JTable(model); 
    sessionTable.setRowHeight(15);

    JScrollPane scrollPane = new JScrollPane(sessionTable);
    add(scrollPane, BorderLayout.CENTER);

    //back button 
    JButton backButton = new JButton("Back");
    backButton.addActionListener(e -> {
        dispose(); // Close this window
        if (previousScreen != null) {
            previousScreen.setVisible(true); // Go back to WHOEVER opened this (Student or Coordinator)
        }
        });
        
        JPanel buttonPanel = new JPanel(); 
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH); 
    

    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


  }
}
