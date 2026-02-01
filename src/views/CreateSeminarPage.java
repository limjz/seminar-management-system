package views;

import controllers.SeminarController;
import java.awt.*;
import javax.swing.*;
import models.DateSelector;
import utils.Config; 

public class CreateSeminarPage extends JFrame {

  private final JTextField titleField;
  private final DateSelector seminarDateBox;

  public CreateSeminarPage(){ 
    super("Create Seminar");
    //setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT); 
    
    JPanel formPanel = new JPanel(new GridLayout(0,1,10, 10));
    JPanel buttonPanel = new JPanel (new FlowLayout(FlowLayout.CENTER, 20, 10));
  
    // ------ Labels for the seminar details ---------
    JLabel titleLabel = new JLabel("Seminar Title"); 
    JLabel dateLabel = new JLabel("Date (YYYY-MM-DD)");

    // ---------- text field -------------
    titleField = new JTextField(20); 
    seminarDateBox = new DateSelector (); 

    //---------- button ------------
    JButton createSeminarButton = new JButton("Create Seminar"); 
    JButton backButton = new JButton ("Back"); 

    // -------------------- Action Listener ---------------
    createSeminarButton.addActionListener(e->{ 
      
      String title = titleField.getText(); 
      String date = seminarDateBox.getSelectedDate_String();
      
      if (title.isEmpty())
      {
       JOptionPane.showMessageDialog(this, "Please fill in the required field!");
      }
      else 
      {
        boolean successCreate = SeminarController.createSeminar(title, date);

        if (successCreate)
        { 
          JOptionPane.showMessageDialog(this, "Seminar Created Successfully!");
        }
      }
      
    });

    backButton.addActionListener(e ->{
      dispose();     // close current page 
      Config.setCoordinatorDashboardVsible();
    });

    formPanel.add(titleLabel); 
    formPanel.add(titleField);
    formPanel.add(dateLabel); 
    formPanel.add(seminarDateBox);

    buttonPanel.add(backButton); 
    buttonPanel.add(createSeminarButton);

    formPanel.setBorder( BorderFactory.createEmptyBorder(20,20,20,20));

    add(formPanel, BorderLayout.CENTER); 
    add(buttonPanel, BorderLayout.SOUTH);

    pack();
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }


}
