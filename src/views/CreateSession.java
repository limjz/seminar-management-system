package views;

import controllers.CoordinatorController;
import java.awt.*;
import javax.swing.*;
import utils.Config;

public class CreateSession extends JFrame {
  
  public CreateSession() {
    super("Create Session");
    JPanel formPanel = new JPanel();
    
    formPanel.setLayout(new GridLayout(0,1,10,10));
    setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
    
    // labels for session details
    JLabel titleLabel = new JLabel("Session Title");
    JLabel dateLabel = new JLabel("Date");
    JLabel timeLabel = new JLabel("Time");
    JLabel venueLabel = new JLabel("Venue");
    JLabel typeLabel = new JLabel("Sesion Type");

    // text fields for session details
    JTextField titleField = new JTextField(20);
    JTextField dateField = new JTextField(20);
    JTextField timeField = new JTextField(20);
    JTextField venueField = new JTextField(20);  
    
    // choosing option for the type of the seminar session 
    String [] typeOptions = {"Oral", "Poster"}; 
    JComboBox <String> sessionTypeOption = new JComboBox<>(typeOptions);


    JPanel buttonPanel = new JPanel (new FlowLayout(FlowLayout.CENTER, 20, 10));
    // add Create Session button
    JButton createSessionButton = new JButton("Create Session");
    // add Back button
    JButton backButton = new JButton ("Back"); 


    // click button action
    createSessionButton.addActionListener(e -> {
      //get info from text fields
      String name = titleField.getText();
      String date = dateField.getText();  
      String time = timeField.getText();
      String venue = venueField.getText(); 
      String type = sessionTypeOption.getSelectedItem().toString();

      // call controller to create session and store in database
      boolean successCreate = CoordinatorController.createSession(name, date, time, venue, type);

      if (successCreate)
      {
        //pop out message
        JOptionPane.showMessageDialog(this, "Session Created Successfully!");

        //set the text field to empty and first option// manual refresh
        titleField.setText("");
        dateField.setText("");
        timeField.setText("");
        venueField.setText("");
        sessionTypeOption.setSelectedItem("Oral");
      }
    });

    // close current page 
    backButton.addActionListener(e ->{
      dispose();
    });

    // add components to panel
    formPanel.add(titleLabel);
    formPanel.add(titleField);
    formPanel.add(dateLabel);
    formPanel.add(dateField);
    formPanel.add(timeLabel);
    formPanel.add(timeField);
    formPanel.add(venueLabel);
    formPanel.add(venueField);

    formPanel.add (typeLabel);
    formPanel.add (sessionTypeOption);

    buttonPanel.add (backButton);
    buttonPanel.add(createSessionButton);

    formPanel.setBorder( BorderFactory.createEmptyBorder(20,20,20,20));

    // the filling text field will be on the top of the two button
    add(formPanel, BorderLayout.CENTER);
    add(buttonPanel, BorderLayout.SOUTH);

    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

  }

}
