package models;

public class Winner {

      private String studentID; 
      private String title; 
      private int totalScore; 
      private int presentationScore; // this represent the People's Choice award

      //contructor 
      public Winner (String studentID, String title, int totalScore, int presentationScore)
      { 
        this.studentID = studentID; 
        this.title = title; 
        this.totalScore = totalScore; 
        this.presentationScore = presentationScore;
      }

      //Getter 
      public String getStudentID() { return studentID; }
      public String getTitle() { return title; }
      public int getTotalScore() { return totalScore; }
      public int getPresentationScore() { return presentationScore; }
}

