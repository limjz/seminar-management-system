package utils;

import views.*;

public class Config {
  
  public static final int WINDOW_WIDTH = 500; 
  public static final int WINDOW_HEIGHT = 500; 


  public static final String SESSIONS_FILE = "data/sessions.txt";
  public static final String PRESENTATION_FILE = "data/presentation.txt";
  public static final String USER_FILE = "data/user.txt";

  public static final String DELIMITER_READ = "\\|";
  public static final String DELIMITER_WRITE = "|";

  public static void setCoordinatorDashboardVsible ()
  { 
    CoordinatorDashboard CD = new CoordinatorDashboard(); 
    CD.setVisible(true);
  }
}

