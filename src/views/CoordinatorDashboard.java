package view;

import java.awt.*;
import javax.swing.*;
import utils.Config;

public class CoordinatorDashboard extends JFrame {
  public CoordinatorDashboard() {
    super("Coordinator Dashboard");
    setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
    setLocationRelativeTo(null);
    
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();

    gbc.insets = new Insets(10, 10, 10, 10); // add spacing between buttons
    gbc.anchor = GridBagConstraints.CENTER; // center the buttons 
    Dimension btnSize = new Dimension(175, 25); // button size 



    //create new sesssion button 
    JButton createNewSessionButton = new JButton("Create New Session");
    createNewSessionButton.setPreferredSize(btnSize);
    //put at first row 
    gbc.gridx = 0; 
    gbc.gridy = 0;
    panel.add(createNewSessionButton, gbc); 

    
    //assign session button
    JButton assignSessionButton = new JButton ("Assign Session");
    assignSessionButton.setPreferredSize(btnSize);
    //second row 
    gbc.gridy = 1;
    panel.add(assignSessionButton, gbc);
    

    //view all session button 
    JButton viewAllSessionButton = new JButton ("View All Sessions");
    viewAllSessionButton.setPreferredSize(btnSize);
    //third row
    gbc.gridy = 2;
    panel.add(viewAllSessionButton, gbc); 
    
    


    //--------------------------- action listeners ----------------------------
    createNewSessionButton.addActionListener(e -> {

      CreateSession CS = new CreateSession();
      CS.setVisible(true);
      this.setVisible(false);

    });
    
    assignSessionButton.addActionListener(e->{ 

      AssignSessionPage AS = new AssignSessionPage();
      AS.setVisible(true);
      this.setVisible(false);

    });
    
    viewAllSessionButton.addActionListener(e-> { 
      ViewSessionPage VSP = new ViewSessionPage(); 
      
      VSP.setVisible(true);
      this.setVisible(false);
    });






    //panel.setBorder(BorderFactory.createEmptyBorder(25,50,25,50));
    add(panel); 

    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }
}
