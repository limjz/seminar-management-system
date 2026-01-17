package views;

import controllers.SessionController;
import java.awt.*;
import javax.swing.*;
import models.DateSelector;
import utils.Config;

public class CreateSessionPage extends JDialog {
  
  private String seminarID;
  private final JTextField titleField; 
  private final DateSelector sessionDateBox;
  private final JTextField timeField;
  private final JComboBox <String> sessionVenueBox;
  private final JComboBox <String> sessionTypeBox;


  public CreateSessionPage(JFrame parent, String seminarID) {
    super(parent, "Create Session", true);
    this.seminarID = seminarID;
    setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
   

    JPanel formPanel = new JPanel(new GridLayout(0,1,10, 10));  
    JPanel buttonPanel = new JPanel (new FlowLayout(FlowLayout.CENTER, 20, 10));

    // labels for session details
    JLabel titleLabel = new JLabel("Session Title");
    JLabel dateLabel = new JLabel("Date (YYYY-MM-DD)");
    JLabel timeLabel = new JLabel("Time");
    JLabel venueLabel = new JLabel("Venue");
    JLabel typeLabel = new JLabel("Sesion Type");

    // text fields for session details
    titleField = new JTextField(20);
    timeField = new JTextField(20);
   


    // choosing option for the date, venue and type of the seminar session 
    sessionDateBox = new DateSelector();

    String [] venueOption = {"DTC", "MPH", "FCI classroom", "FCM classroom"};
    sessionVenueBox = new JComboBox<>(venueOption);

    String [] typeOptions = {"Oral", "Poster"}; 
    sessionTypeBox = new JComboBox<>(typeOptions);


    // add Create Session button
    JButton createSessionButton = new JButton("Create Session");
    // add Back button
    JButton backButton = new JButton ("Back"); 

    // -------------------- Action Listener ---------------
    createSessionButton.addActionListener(e -> {
      saveSession(seminarID);
    });

    backButton.addActionListener(e ->{
      dispose();
      Config.setSeminarDashboardVsible();
    });

    // add components to panel
    formPanel.add(titleLabel);
    formPanel.add(titleField);

    formPanel.add(dateLabel);
    //formPanel.add(dateField);
    formPanel.add(sessionDateBox);

    formPanel.add(timeLabel);
    formPanel.add(timeField);
    formPanel.add(venueLabel);
    formPanel.add(sessionVenueBox);

    formPanel.add (typeLabel);
    formPanel.add (sessionTypeBox);

    buttonPanel.add (backButton);
    buttonPanel.add(createSessionButton);

    formPanel.setBorder( BorderFactory.createEmptyBorder(20,20,20,20));

    // the filling text field will be on the top of the two button
    add(formPanel, BorderLayout.CENTER);
    add(buttonPanel, BorderLayout.SOUTH);

    //pack();
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

  }

  private void saveSession (String seminarID) 
  { 
    //get info from text fields
      String name = titleField.getText();
      String date = sessionDateBox.getSelectedDate_String();  
      String time = timeField.getText();
      String venue = sessionVenueBox.getSelectedItem().toString(); 
      String type = sessionTypeBox.getSelectedItem().toString();

      if (name.isEmpty() && time.isEmpty())
      {
        JOptionPane.showMessageDialog(this, "Please fill all the required field!");

      }
      else 
      {
        // call controller to create session and store in database
        boolean successCreate = SessionController.createSession(seminarID, name, date, time, venue, type);

        if (successCreate)
        {
          //pop out message
          JOptionPane.showMessageDialog(this, "Session Created Successfully!");

          //set the text field to empty and first option// manual refresh
          titleField.setText("");
          timeField.setText("");
          //sessionVenueBox.setSelectedItem("");
          //sessionTypeBox.setSelectedItem("");
        }
      }
      
  }


}
