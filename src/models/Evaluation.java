package models;

import utils.Config;

public class Evaluation {

    private String evaluatorId;
    private String seminarId; 
    private String studentId;
    private String submissionId;
    
   
    //rubrics           //     Poster            Oral
    private int score1; // Content Quality @ Problem Clarity
    private int score2; // Visual Design   @ Methodology
    private int score3; // Organization    @ Results
    private int score4; // Explanation     @ Presentation

    private String comments;
    private int totalScore;


    public Evaluation(String evaluatorId, String seminarId, String studentId, String submissionId,
                      int score1, int score2, int score3, int score4,
                      String comments, int totalScore) 
    {
        this.evaluatorId = evaluatorId;
        this.seminarId = seminarId;
        this.studentId = studentId;
        this.submissionId = submissionId;

        this.score1 = score1;
        this.score2 = score2;
        this.score3 = score3;
        this.score4 = score4;

        this.comments = comments;
        this.totalScore = totalScore;
    }


    public String toFileLine() {
        return 
            evaluatorId + Config.DELIMITER_WRITE +
            seminarId + Config.DELIMITER_WRITE +
            studentId + Config.DELIMITER_WRITE + 
            submissionId + Config.DELIMITER_WRITE +
            score1 + Config.DELIMITER_WRITE +
            score2 + Config.DELIMITER_WRITE +
            score3 + Config.DELIMITER_WRITE +
            score4 + Config.DELIMITER_WRITE +
            comments + Config.DELIMITER_WRITE + 
            totalScore;
    }

    public static Evaluation fromFileLine(String line) {
        String[] parts = line.split(Config.DELIMITER_READ);
        if (parts.length < 10) return null;

        return new Evaluation(
                parts[0].trim(), //evaluatorID 
                parts[1].trim(), //seminarID 
                parts[2].trim(), //studentID
                parts[3].trim(), //submissionID
                parseIntSafe(parts[4]), //score1
                parseIntSafe(parts[5]), //score2
                parseIntSafe(parts[6]), //score3
                parseIntSafe(parts[7]), //score4
                parts[8].trim(), //comment 
                parseIntSafe(parts[9]) //totalScore
        );
    }

    private static int parseIntSafe(String s) {
        try { return Integer.parseInt(s.trim()); }
        catch (Exception e) { return 0; }
    }

    
    // getters
    public String getEvaluatorId() { return evaluatorId; }
    public String getSeminarId() { return seminarId; }
    public String getStudentId() {return studentId; }
    public String getSubmissionId() { return submissionId; }
    public int getScore1() { return score1; }
    public int getScore2() { return score2; }
    public int getScore3() { return score3; }
    public int getScore4() { return score4; }
    public String getComments() { return comments; }
    public int getTotalScore() {return score1 + score2 + score3 + score4;}
}


