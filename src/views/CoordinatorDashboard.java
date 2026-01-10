package views;

import controllers.CoordinatorController;
import java.awt.GridLayout;
import javax.swing.*;

public class CoordinatorDashboard extends JFrame {
  
  public CoordinatorDashboard() {
    super("Coordinator Dashboard");
    JPanel panel = new JPanel();
    
    panel.setLayout(new GridLayout(0,1,10,10));
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
    


    // add Create Session button
    JButton createSessionButton = new JButton("Create Session");
    panel.add(createSessionButton);

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

    // add components to panel
    panel.add(titleLabel);
    panel.add(titleField);
    panel.add(dateLabel);
    panel.add(dateField);
    panel.add(timeLabel);
    panel.add(timeField);
    panel.add(venueLabel);
    panel.add(venueField);
    panel.add(createSessionButton);

    panel.setBorder( BorderFactory.createEmptyBorder(20,20,20,20));

    add(panel);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setVisible(true);
  }

}
