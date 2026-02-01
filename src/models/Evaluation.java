package models;

import utils.Config;

public class Evaluation {

    private String evaluatorId;
    private String submissionId;
    
    private String studentId;
    private String sessionID; 
    

    //rubric
    private int problemClarity;
    private int methodology;
    private int results;
    private int presentation;

    private String comments;
    private int totalScore;


    public Evaluation(String evaluatorId, String submissionId, String studentId,
                      int problemClarity, int methodology, int results, int presentation,
                      String comments, int totalScore) 
    {
        this.evaluatorId = evaluatorId;
        this.submissionId = submissionId;
        this.studentId = studentId;

        this.problemClarity = problemClarity;
        this.methodology = methodology;
        this.results = results;
        this.presentation = presentation;

        this.comments = comments;
        this.totalScore = totalScore;
    }



    public String toFileLine() {
        return 
            evaluatorId + Config.DELIMITER_WRITE +
            submissionId + Config.DELIMITER_WRITE +
            problemClarity + Config.DELIMITER_WRITE +
            methodology + Config.DELIMITER_WRITE +
            results + Config.DELIMITER_WRITE +
            presentation + Config.DELIMITER_WRITE +
            comments + Config.DELIMITER_WRITE + 
            totalScore;
    }

    public static Evaluation fromFileLine(String line) {
        // split into max 8 parts so comments can contain "|"? (we keep it simple)
        String[] parts = line.split(Config.DELIMITER_READ, 9);
        if (parts.length < 9) return null;

        return new Evaluation(
                parts[0].trim(),
                parts[1].trim(),
                parts[2].trim(),
                parseIntSafe(parts[3]),
                parseIntSafe(parts[4]),
                parseIntSafe(parts[5]),
                parseIntSafe(parts[6]),
                parts[7].trim(),
                parseIntSafe(parts[8])
        );
    }

    private static int parseIntSafe(String s) {
        try { return Integer.parseInt(s.trim()); }
        catch (Exception e) { return 0; }
    }

    

    // getters
    public String getEvaluatorId() { return evaluatorId; }
    public String getSubmissionId() { return submissionId; }
    public String getStudentId() {return studentId; }
    public int getProblemClarity() { return problemClarity; }
    public int getMethodology() { return methodology; }
    public int getResults() { return results; }
    public int getPresentation() { return presentation; }
    public String getComments() { return comments; }
    public int getTotalScore() {return problemClarity + methodology + results + presentation;}
}


