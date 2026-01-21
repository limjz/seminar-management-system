package models;

import utils.Config;

public class Presentation {
    private String presID;
    private String studentID;
    private String title;
    private String abstractText;
    private String supervisor;
    private String type;
    private String sessionID;
    private String material;

    // Constructor
    public Presentation(String presID, String studentID, String title, String abstractText, 
                        String supervisor, String type, String sessionID, String material) {
        this.presID = presID;
        this.studentID = studentID;
        this.title = title;
        this.abstractText = abstractText;
        this.supervisor = supervisor;
        this.type = type;
        this.sessionID = sessionID;
        this.material = material;
    }

    // Convert Object -> File String
    public String toFileLine() {
        return presID + Config.DELIMITER_WRITE +
               studentID + Config.DELIMITER_WRITE +
               title + Config.DELIMITER_WRITE +
               abstractText + Config.DELIMITER_WRITE +
               supervisor + Config.DELIMITER_WRITE +
               type + Config.DELIMITER_WRITE +
               sessionID + Config.DELIMITER_WRITE +
               material;
    }

    // Convert File String -> Object
    public static Presentation fromFileLine(String line) {
        String[] parts = line.split(Config.DELIMITER_READ);
        
        // Ensure we have at least 8 parts (even if material is "null")
        if (parts.length < 8) return null;

        return new Presentation(
            parts[0], // presID
            parts[1], // studentID
            parts[2], // title
            parts[3], // abstract
            parts[4], // supervisor
            parts[5], // type
            parts[6], // sessionID
            parts[7]  // material
        );
    }

    // Getters
    public String getPresID() { return presID; }
    public String getStudentID() { return studentID; }
    public String getTitle() { return title; }
    public String getAbstractText() { return abstractText; }
    public String getSupervisor() { return supervisor; }
    public String getType() { return type; }
    public String getSessionID() { return sessionID; }
    public String getMaterial() { return material; }

    // Setters (Only the ones you might update)
    public void setMaterial(String material) { this.material = material; }
}