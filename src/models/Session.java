package models;

import utils.Config;



public class Session {
  private String sessionID; 
  private String seminarID;
  private String sessionName;
  private String sessionDate;
  private String sessionTime;
  private String sessionVenue;
  private String sessionType;
  private String evaluator; 
  private String presenter; 
  private String boardID ; // only for the poster presenter, oral will be null 


  // Constructor  
  public Session (String sessionID, String seminarID, String sessionName, String sessionDate, String sessionTime, String sessionVenue, String sessionType, String evaluator, String presenter, String boardID) {
    this.sessionID = sessionID;
    this.seminarID = seminarID;
    this.sessionName = sessionName;
    this.sessionDate = sessionDate;
    this.sessionTime = sessionTime;
    this.sessionVenue = sessionVenue;
    this.sessionType = sessionType;  
    this.evaluator = evaluator; 
    this.presenter = presenter; 
    this.boardID = (boardID == null || boardID.isEmpty()) ? " - " : boardID; // if boardID is empty then put it " - ", else put the real boardID
  }

  //convert data from object to line string
  public String toFileLine (){
    return this.sessionID + Config.DELIMITER_WRITE + this.seminarID+ Config.DELIMITER_WRITE + this.sessionName + Config.DELIMITER_WRITE + this.sessionDate + Config.DELIMITER_WRITE + this.sessionTime + Config.DELIMITER_WRITE + this.sessionVenue + Config.DELIMITER_WRITE + this.sessionType + Config.DELIMITER_WRITE + this.evaluator + Config.DELIMITER_WRITE + this.presenter + Config.DELIMITER_WRITE + this.boardID;
  }

   //convert data from line string to object 
  public static Session fromFileLine (String line){ 

    String[] session_data = line.split(Config.DELIMITER_READ);
    if (line == null || session_data.length < 9)
    { 
      return null;
    }

    String evaluator = "Unassigned";
    String presenter = "Unassigned";
    String bID = " - "; 


    if (session_data.length > 7)
    { 
      evaluator = session_data[7];
    }

    if (session_data.length > 8)
    { 
      presenter = session_data[8];
    }

    if (session_data.length >= 10 )
    { 
      bID = session_data[9].trim();
    }

    return new Session(
      session_data[0].trim(),
      session_data[1].trim(), 
      session_data[2].trim(),
      session_data[3].trim(),
      session_data[4].trim(),
      session_data[5].trim(), 
      session_data[6].trim(),
      evaluator,
      presenter,
      bID
    );
  }




  //Getter 
  public String getSessionID() { return this.sessionID; }
  public String getSeminarID () { return this.seminarID; }
  public String getSessionName() { return this.sessionName; }
  public String getSessionDate() { return this.sessionDate; }
  public String getSessionTime() { return this.sessionTime; }
  public String getSessionVenue() { return this.sessionVenue; }
  public String getSessionType () { return this.sessionType; }
  public String getEvaluator() { return this.evaluator; } 
  public String getPresenter() { return this.presenter; }
  public String getBoardID () { return this.boardID; }

  //Setter 
  public void setSessionName(String sessionName) { this.sessionName = sessionName; }
  public void setSessionDate(String sessionDate) { this.sessionDate = sessionDate; }
  public void setSessionTime(String sessionTime) { this.sessionTime = sessionTime; } 
  public void setSessionVenue(String sessionVenue) { this.sessionVenue = sessionVenue; }
  public void setSessionType(String sessionType) { this.sessionType = sessionType; }
  public void setEvaluator (String evaluator) {this.evaluator = evaluator; }
  public void setPresenter (String presenter) {this.presenter = presenter; }
  public void setBoardID (String bId) {this.boardID = bId; }

}
