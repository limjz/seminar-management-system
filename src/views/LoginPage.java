package views;

import controllers.LoginController;
import java.awt.*;
import javax.swing.*;
import models.User;
import utils.Config;

public class LoginPage extends JFrame {

  // constructor
  public LoginPage() {

    super("Login");

    JPanel pagePanel = new JPanel();
    setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT / 2);
    setLocationRelativeTo(null);

    pagePanel.setLayout(new GridLayout(3, 2, 10, 10)); // grid layout

    // labels
    JLabel userLabel = new JLabel("Username:");
    JLabel pwdLabel = new JLabel("Password:");

    // text fields
    JTextField userText = new JTextField();
    JPasswordField pwdText = new JPasswordField();

    // button
    JButton loginButton = new JButton("Login"); // Login button

    // center the button
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    buttonPanel.add(loginButton);

    // login click action
    loginButton.addActionListener(e -> {
      String username = userText.getText().toUpperCase();
      String password = new String(pwdText.getPassword());

      User user = LoginController.authentication(username, password);

      if (user != null) {
        openDashboard(user);
        this.dispose(); // close login after successful login
      } else {
        JOptionPane.showMessageDialog(this, "Invalid ID or Password", "Login Error", JOptionPane.ERROR_MESSAGE);
        userText.setText("");
        pwdText.setText("");
      }
    });

    // add components to panel
    pagePanel.add(userLabel);
    pagePanel.add(userText);
    pagePanel.add(pwdLabel);
    pagePanel.add(pwdText);

    // spacing
    pagePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    add(pagePanel, BorderLayout.CENTER);
    add(buttonPanel, BorderLayout.SOUTH);

    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  /**
   * Open dashboard based on role after login.
   */
  private void openDashboard(User user) {
    String role = user.getUserRole();

    if (role.equals("Coordinator")) {
      new CoordinatorDashboard().setVisible(true);
      return;
    }
    if (role.equals("Student"))
    { 
      // put the student dashboard here, after login will direct to student dashboard
      new StudentDashboard(user).setVisible(true);
      return;
    }

    if (role.equals("Evaluator")) {
      // pass evaluator ID so EvaluationPage can filter assigned submissions
      new EvaluationPage(user.getUserID()).setVisible(true);
      return;
    }

    JOptionPane.showMessageDialog(this, "Unknown role: " + role, "Role Error", JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Main method so you can run LoginPage directly:
   * java -cp bin views.LoginPage
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new LoginPage().setVisible(true));
  }
}
