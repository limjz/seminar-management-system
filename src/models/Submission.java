package models;

import utils.Config;

public class Submission {
    private final String subID;
    private final String studentID;
    private final String title;
    private final String abstractText;
    private final String supervisor;
    private final String type;
    private final String boardID; // only for the poster submission, oral will be null
    private final String seminarID;
    private String material;

    // Constructor
    public Submission(String subID, String studentID, String title, String abstractText, 
                        String supervisor, String type, String boardID, String seminarID, String material) {
        this.subID = subID;
        this.studentID = studentID;
        this.title = title;
        this.abstractText = abstractText;
        this.supervisor = supervisor;
        this.type = type;
        this.boardID = (boardID == null || boardID.isEmpty()) ? "-" : boardID;
        this.seminarID = seminarID;
        this.material = material;
    }

    // Convert Object -> File String
    public String toFileLine() {
        return subID + Config.DELIMITER_WRITE +
               studentID + Config.DELIMITER_WRITE +
               title + Config.DELIMITER_WRITE +
               abstractText + Config.DELIMITER_WRITE +
               supervisor + Config.DELIMITER_WRITE +
               type + Config.DELIMITER_WRITE +
               boardID + Config.DELIMITER_WRITE +
               seminarID + Config.DELIMITER_WRITE +
               material;
    }

    // Convert File String -> Object
    public static Submission fromFileLine(String line) {
        String[] parts = line.split(Config.DELIMITER_READ);
        
        // Ensure we have at least 8 parts (even if material is "null")
        if (parts.length < 9) return null;

        String bID = " - ";

        if (parts.length >= 9 )
        { 
            bID = parts[6].trim();
        }

        return new Submission(
            parts[0], // subID
            parts[1], // studentID
            parts[2], // title
            parts[3], // abstract
            parts[4], // supervisor
            parts[5], // type
            bID, // boardID
            parts[7],  // seminarID 
            parts[8]    // material 
        );
    }

    // Getters
    public String getSubmissionID() { return subID; }
    public String getStudentID() { return studentID; }
    public String getTitle() { return title; }
    public String getAbstractText() { return abstractText; }
    public String getSupervisor() { return supervisor; }
    public String getType() { return type; }
    public String getBoardID () { return boardID; }
    public String getSeminarID() { return seminarID; }
    public String getMaterial() { return material; }


    // Setters (Only the ones you might update)
    public void setMaterial(String material) { this.material = material; }
}