package views;

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
    Dimension btnSize = new Dimension(200, 25); // button size 


    //create new seminar button 
    JButton createNewSeminarButton = new JButton("Create New Seminar");
    createNewSeminarButton.setPreferredSize(btnSize);
    //put at first row 
    gbc.gridx = 0; 
    gbc.gridy = 0;
    panel.add(createNewSeminarButton, gbc); 


    //view all seminar button 
    JButton viewSeminarButton = new JButton ("View Seminars");
    viewSeminarButton.setPreferredSize(btnSize);
    //second row
    gbc.gridy = 1;
    panel.add(viewSeminarButton, gbc); 

    //report button 
    JButton reportButton = new JButton("<html><center>View Seminar Schedule <br> And Evaluation Reports</center></html>"); //html to wrap the text in the button 
    reportButton.setPreferredSize(new Dimension(200, 35));
    //third row
    gbc.gridy = 2;
    panel.add(reportButton, gbc);

    JButton awardButton = new JButton ("<html><center>View Award Ceremony <br> Schedule </center></html>"); 
    awardButton.setPreferredSize(new Dimension(200, 35));
    gbc.gridy = 3;
    panel.add(awardButton, gbc);

    //logout button 
    JButton logoutButton = new JButton("Logout");
    logoutButton.setPreferredSize(btnSize);
    gbc.gridy = 4; 
    panel.add(logoutButton, gbc);

    
    //--------------------------- Action listeners ----------------------------
    createNewSeminarButton.addActionListener(e -> {

      CreateSeminarPage CS = new CreateSeminarPage();
      CS.setVisible(true);
      this.setVisible(false);

    });
    
    
    viewSeminarButton.addActionListener(e-> { 

      SeminarDashboard SD = new SeminarDashboard(); 
      SD.setVisible(true);
      this.setVisible(false);

    });

    reportButton.addActionListener(e->{ 

      GenerateReportPage GRP = new GenerateReportPage(); 
      GRP.setVisible(true);
      this.setVisible(false);

    });

    awardButton.addActionListener(e-> { 
      
      AwardCeremonyPage ACP = new AwardCeremonyPage(); 
      ACP.setVisible(true);
      this.setVisible(false);

    });


    logoutButton.addActionListener(e-> Config.backToLogin(this));



    //panel.setBorder(BorderFactory.createEmptyBorder(25,50,25,50));
    add(panel); 

    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }
}
