package models;

import utils.Config;

public class Seminar {

  private String seminarID;
  private String seminarTitle; 
  private String seminarDate;
  
  public Seminar (String id, String title, String data){ 
    this.seminarID = id; 
    this.seminarTitle = title; 
    this.seminarDate = data; 
    
  }

  public String toFileLine () { 
    return this.seminarID + Config.DELIMITER_WRITE + this.seminarTitle + Config.DELIMITER_WRITE+ this.seminarDate;
  }
  
  public static Seminar fromFileLine (String line) { 
    String[] seminar_data = line.split (Config.DELIMITER_READ); 
    if (line == null || seminar_data.length < 3) { 
      return null; 
    }

    return new Seminar ( 
      seminar_data[0],
      seminar_data[1],
      seminar_data[2]
    );
  }


  //getter
  public String getSeminarID() { return seminarID; }
  public String getSeminarTitle() { return seminarTitle;}
  public String getSeminarDate() { return seminarDate;}
  
  
  //setter 
  public void setSeminarID(String seminarID) { this.seminarID = seminarID; }
  public void setSeminarTitle(String seminarTitle) { this.seminarTitle = seminarTitle; }
  public void setSeminarDate(String seminarDate) { this.seminarDate = seminarDate; }


}
