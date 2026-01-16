package main;

import view.LoginUI;
import javax.swing.SwingUtilities;

public class MainEvaluator {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new LoginUI().setVisible(true));
  }
}



