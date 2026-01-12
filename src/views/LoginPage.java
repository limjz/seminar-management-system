package views;

import controllers.LoginController;
import java.awt.*;
import javax.swing.*;
import models.User;
import utils.Config;

public class LoginPage extends JFrame{
  
  // constructor 
  public LoginPage () 
  { 
    // create window 
        super ("Login");

        JPanel pagePanel = new JPanel();
        pagePanel.setLayout( new GridLayout(3,2, 10, 10)); // grid layout
        setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT/2);


        //labels 
        JLabel userLabel = new JLabel("Username:");
        JLabel pwdLabel = new JLabel("Password:");

        //text 
        JTextField userText = new JTextField(); 
        JPasswordField pwdText = new JPasswordField();

        //button
        JButton loginButton = new JButton ("Login"); //Login button 
        // to centre the button 
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
        buttonPanel.add(loginButton);


        loginButton.addActionListener(e -> {
            String username = userText.getText(); 
            String password = new String (pwdText.getPassword()); // getPassword returns char array
                                                                 // Convert char to string => new String(char[]
            User user = LoginController.authentication(username, password);
            if (user != null) // user found
            { 
              //JOptionPane.showMessageDialog(this, "Login Successfully!\n" );
              openDashboard(user);
              
              this.dispose();
            }
            
            else 
            { 
              JOptionPane.showMessageDialog(this, "Invalid ID or Password", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
            

        });

        //add components to window (pagePanel)
        pagePanel.add (userLabel);
        pagePanel.add (userText);
        pagePanel.add (pwdLabel);
        pagePanel.add (pwdText);
        pagePanel.add (buttonPanel);

        pagePanel.setBorder( BorderFactory.createEmptyBorder(20,20,20,20));

        add(pagePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

  }




  private void openDashboard (User user)
  { 
    String role = user.getUserRole();

    if (role.equals("Coordinator"))
    { 
      CoordinatorDashboard coordinatorDashboard = new CoordinatorDashboard();
      coordinatorDashboard.setVisible(true);
    }
    if (role.equals("Student"))
    { 
      // put the student dashboard here, after login will direct to student dashboard
      JOptionPane.showMessageDialog(this, "Student Dashboard coming soon!");

    }
    if (role.equals("Evaluator"))
    { 
      // put the evaluator dashboard here, after login will direct to student dashboard
      JOptionPane.showMessageDialog(this, "Evaluator Dashboard coming soon!");
    }
  }
}
