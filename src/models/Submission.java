package models;

import utils.Config;

public class Submission {

    private String submissionId;
    private String studentId;
    private String title;
    private String type;     // Oral / Poster
    private String filePath; // optional

    public Submission(String submissionId, String studentId, String title, String type, String filePath) {
        this.submissionId = submissionId;
        this.studentId = studentId;
        this.title = title;
        this.type = type;
        this.filePath = filePath;
    }

    // object -> txt line
    public String toFileLine() {
        return submissionId + Config.DELIMITER_WRITE +
               studentId + Config.DELIMITER_WRITE +
               title + Config.DELIMITER_WRITE +
               type + Config.DELIMITER_WRITE +
               filePath;
    }

    // txt line -> object
    public static Submission fromFileLine(String line) {
        String[] parts = line.split(Config.DELIMITER_READ, -1);
        if (parts.length < 5) return null;
        return new Submission(parts[0], parts[1], parts[2], parts[3], parts[4]);
    }

    // getters
    public String getSubmissionId() { return submissionId; }
    public String getStudentId() { return studentId; }
    public String getTitle() { return title; }
    public String getType() { return type; }
    public String getFilePath() { return filePath; }
}

