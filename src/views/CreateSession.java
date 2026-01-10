package views;

import controllers.CoordinatorController;
import java.awt.*;
import javax.swing.*;

public class CreateSession extends JFrame {
  
  public CreateSession() {
    super("Create Session");
    JPanel formPanel = new JPanel();
    
    formPanel.setLayout(new GridLayout(0,1,10,10));
    setSize(500, 400);
    
    // labels for session details
    JLabel titleLabel = new JLabel("Session Title");
    JLabel dateLabel = new JLabel("Date");
    JLabel timeLabel = new JLabel("Time");
    JLabel venueLabel = new JLabel("Venue");

    // text fields for session details
    JTextField titleField = new JTextField(20);
    JTextField dateField = new JTextField(20);
    JTextField timeField = new JTextField(20);
    JTextField venueField = new JTextField(20);  
    

    JPanel buttonPanel = new JPanel (new FlowLayout(FlowLayout.CENTER, 20, 10));
    // add Create Session button
    JButton createSessionButton = new JButton("Create Session");
    // add Back button
    JButton backButton = new JButton ("Back"); 


    // click button action
    createSessionButton.addActionListener(e -> {
      //get info from text fields
      String v = venueField.getText(); 
      String n = titleField.getText(); 
      String d = dateField.getText();
      String t = timeField.getText();

      // call controller to create session and store in database
      boolean successCreate = CoordinatorController.createSession(n, d, t, v);

      if (successCreate)
      {
        JOptionPane.showMessageDialog(this, "Session Created Successfully!");

        //set the text field to empty// manual refresh
        titleField.setText("");
        dateField.setText("");
        timeField.setText("");
        venueField.setText("");
      }
    });

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
    buttonPanel.add (backButton);
    buttonPanel.add(createSessionButton);

    formPanel.setBorder( BorderFactory.createEmptyBorder(20,20,20,20));

    // the filling text field will be on the top of the two button
    add(formPanel, BorderLayout.CENTER);
    add(buttonPanel, BorderLayout.SOUTH);

    setDefaultCloseOperation(EXIT_ON_CLOSE);

  }

}
