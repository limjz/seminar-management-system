package view;

import javax.swing.*;

public class CoordinatorDashboard extends JFrame {
  public CoordinatorDashboard() {
    super("Coordinator Dashboard");
    JPanel panel = new JPanel();
    setSize(500, 400);


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
