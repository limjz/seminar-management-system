package models;

import utils.Config;

public class User {
  private String id; 
  private String name;
  private String password; 
  private String role; 
  

  // constructor 
  public User (String ID, String Name, String Pwd, String Role){
    this.id = ID; 
    this.name = Name; 
    this.password = Pwd; 
    this.role = Role;
  }

  // convert data from object to line string 
  public String toFileLine (){ 
    return this.id + Config.DELIMITER_WRITE + this.name + Config.DELIMITER_WRITE + this.password + Config.DELIMITER_READ + this.role;
  }

  //convert data from line string to object 
  public static User fromFileLine (String line){ 

    if (line == null)
    { 
      return null;
    }

    String[] user_data = line.split(Config.DELIMITER_READ);

    return new User(
      user_data[0],
      user_data[1], 
      user_data[2],
      user_data[3]
    );
  }


  // getter 
  public String getUserID () {return this.id; }
  public String getUserName () {return this.name; }
  public String getUserPwd () { return this.password; }
  public String getUserRole () { return this.role; }

  // setter 
  public void setUserID (String ID) { this.id = ID; }
  public void setUserName (String Name) { this.name = Name; }
  public void setUserPwd (String Pwd) { this.password = Pwd; }
  public void setUserRole (String Role) { this.role = Role; }
  


}
