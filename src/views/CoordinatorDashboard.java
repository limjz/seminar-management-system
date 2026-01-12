package views;

import javax.swing.*;
import utils.Config;

public class CoordinatorDashboard extends JFrame {
  public CoordinatorDashboard() {
    super("Coordinator Dashboard");
    JPanel panel = new JPanel();
    setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);


    //button 
    JButton createNewSessionButton = new JButton("Create New Session");
    panel.add(createNewSessionButton); 

    createNewSessionButton.addActionListener((actionEvent) -> {

      CreateSession CS = new CreateSession();
      CS.setVisible(true);
    });

    add(panel); 
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }
}
