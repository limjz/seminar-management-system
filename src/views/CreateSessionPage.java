package views;

import controllers.SessionController;
import java.awt.*;
import javax.swing.*;
import models.DateSelector;
import utils.Config;

public class CreateSessionPage extends JDialog {
  
  private final String seminarID;
  private final JTextField titleField; 
  private final DateSelector sessionDateBox;

  // date drop down  
  private final JComboBox<String> hourBox; 
  private final JComboBox<String> minuteBox; 
  private final JComboBox<String> amPmBox; 

  private final JComboBox <String> sessionVenueBox;
  private final JComboBox <String> sessionTypeBox;


  public CreateSessionPage(JFrame parent, String seminarID) {
    super(parent, "Create Session", true);
    this.seminarID = seminarID;
    setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
   

    JPanel formPanel = new JPanel(new GridLayout(0,1,10, 10));  
    JPanel buttonPanel = new JPanel (new FlowLayout(FlowLayout.CENTER, 20, 10));
    JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5,0));

    // ----------- labels  ---------
    JLabel titleLabel = new JLabel("Session Title");
    JLabel dateLabel = new JLabel("Date (YYYY-MM-DD)");
    JLabel timeLabel = new JLabel("Time");
    JLabel venueLabel = new JLabel("Venue");
    JLabel typeLabel = new JLabel("Sesion Type");

    // ---------- text fields -------------
    titleField = new JTextField(20);

    
    hourBox = new JComboBox<> (generateNumber (1,12));
    minuteBox = new JComboBox<>(generateMinutes(15));
    amPmBox = new JComboBox<>(new String[]{"AM", "PM"});

    //----------- Session Date, Venue & Type Drop Down----------
    sessionDateBox = new DateSelector();

    String [] venueOption = {"DTC", "MPH", "FCI classroom", "FCM classroom"};
    sessionVenueBox = new JComboBox<>(venueOption);

    String [] typeOptions = {"Oral", "Poster"}; 
    sessionTypeBox = new JComboBox<>(typeOptions);


    // ----------- Button ------------
    JButton createSessionButton = new JButton("Create Session");
    JButton backButton = new JButton ("Back"); 


    // --------------- Action Listener ------------------
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
    formPanel.add(sessionDateBox);

    timePanel.add(hourBox); 
    timePanel.add(new JLabel(":")); 
    timePanel.add(minuteBox); 
    timePanel.add(amPmBox); 
    formPanel.add(timeLabel);
    formPanel.add(timePanel);


    formPanel.add(venueLabel);
    formPanel.add(sessionVenueBox);

    formPanel.add (typeLabel);
    formPanel.add (sessionTypeBox);

    buttonPanel.add (backButton);
    buttonPanel.add(createSessionButton);

    formPanel.setBorder( BorderFactory.createEmptyBorder(20,20,20,20));

    // text field will be on the top of the two button
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
      String time = getSelectedTime();
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
        }
      }
      
  }


  private String[] generateNumber (int start, int end){ 
    
    int size = end - start + 1; 
    String [] numbers = new String [size]; 

    for (int i = 0; i< size; i ++)
    { 
      int val = start + i; 
      if (val < 10)
      { 
        numbers[i] = "0" + val; 
      }
      else
      { 
        numbers [i] = "" + val;
      }
    }
    return numbers; 
  }

  private String[] generateMinutes (int step){ 

    int size = 60 / step; 
    String [] minutes = new String [size]; 
    
    for (int i = 0; i < size; i++)
    { 
      int val = i*step; 

      if (val < 10)
      { 
        minutes [i] = "0" + val; 
      }
      else
      { 
        minutes [i] = "" + val;
      }
    }

    return minutes;
  } 


  private String getSelectedTime()
  { 
    String hour = (String) hourBox.getSelectedItem(); 
    String minute = (String) minuteBox.getSelectedItem(); 
    String AP = (String) amPmBox.getSelectedItem(); 

    return hour + ":" + minute + " " + AP;

  }
}
