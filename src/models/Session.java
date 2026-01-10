package models;

import utils.Config;



public class Session {
  private String sessionID; 
  private String sessionName;
  private String sessionDate;
  private String sessionTime;
  private String sessionVenue;
  private String sessionType;

  // Constructor  
  public Session (String sessionID, String sessionName, String sessionDate, String sessionTime, String sessionVenue, String sessionType) {
    this.sessionID = sessionID;
    this.sessionName = sessionName;
    this.sessionDate = sessionDate;
    this.sessionTime = sessionTime;
    this.sessionVenue = sessionVenue;
    this.sessionType = sessionType;  
  }

  //convert data from object to line string
  public String toFileLine (){
    return this.sessionID + Config.DELIMITER_WRITE + this.sessionName + Config.DELIMITER_WRITE + this.sessionDate + Config.DELIMITER_WRITE + this.sessionTime + Config.DELIMITER_WRITE + this.sessionVenue + Config.DELIMITER_WRITE + this.sessionType;
  }

  //Getter 
  public String getSessionID() { return this.sessionID; }
  public String getSessionName() { return this.sessionName; }
  public String getSessionDate() { return this.sessionDate; }
  public String getSessionTime() { return this.sessionTime; }
  public String getSessionVenue() { return this.sessionVenue; }
  public String getSessionType () { return this.sessionType; }

  //Setter 
  public void setSessionName(String sessionName) { this.sessionName = sessionName; }
  public void setSessionDate(String sessionDate) { this.sessionDate = sessionDate; }
  public void setSessionTime(String sessionTime) { this.sessionTime = sessionTime; } 
  public void setSessionVenue(String sessionVenue) { this.sessionVenue = sessionVenue; }
  public void setSessionType(String sessionType) { this.sessionType = sessionType; }
}
