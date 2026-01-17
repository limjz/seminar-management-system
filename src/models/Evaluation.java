package models;

import utils.Config;

public class Evaluation {

    private String evaluatorId;
    private String submissionId;
    

    private int problemClarity;
    private int methodology;
    private int results;
    private int presentation;

    private String comments;

    public Evaluation(String evaluatorId, String submissionId, 
                      int problemClarity, int methodology, int results, int presentation,
                      String comments) {
        this.evaluatorId = evaluatorId;
        this.submissionId = submissionId;
        
        this.problemClarity = problemClarity;
        this.methodology = methodology;
        this.results = results;
        this.presentation = presentation;
        this.comments = comments;
    }

    public String toFileLine() {
        return 
            evaluatorId + Config.DELIMITER_WRITE +
            submissionId + Config.DELIMITER_WRITE +
            problemClarity + Config.DELIMITER_WRITE +
            methodology + Config.DELIMITER_WRITE +
            results + Config.DELIMITER_WRITE +
            presentation + Config.DELIMITER_WRITE +
            comments;
    }

    public static Evaluation fromFileLine(String line) {
        // split into max 7 parts so comments can contain "|"? (we keep it simple)
        String[] parts = line.split(Config.DELIMITER_READ, 7);
        if (parts.length < 7) return null;

        return new Evaluation(
                parts[0],
                parts[1],
                parseIntSafe(parts[2]),
                parseIntSafe(parts[3]),
                parseIntSafe(parts[4]),
                parseIntSafe(parts[5]),
                parts[6]
        );
    }

    private static int parseIntSafe(String s) {
        try { return Integer.parseInt(s.trim()); }
        catch (Exception e) { return 0; }
    }

    public int getTotalScore() {
        return problemClarity + methodology + results + presentation;
    }

    // getters
    public String getEvaluatorId() { return evaluatorId; }
    public String getSubmissionId() { return submissionId; }
    public int getProblemClarity() { return problemClarity; }
    public int getMethodology() { return methodology; }
    public int getResults() { return results; }
    public int getPresentation() { return presentation; }
    public String getComments() { return comments; }
}


