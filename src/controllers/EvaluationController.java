package controllers;

import java.util.ArrayList;
import java.util.List;
import models.Evaluation;
import models.Submission;
import utils.Config;
import utils.FileHandler;

public class EvaluationController {

    // Get submissions assigned to evaluator (using assignments.txt)
    public static List<Submission> getAssignedSubmissions(String evaluatorId) {

        List<String> sessionLines = FileHandler.readAllLines(Config.SESSIONS_FILE);
        //List<String> assignmentLines = FileHandler.readAllLines(Config.ASSIGNMENTS_FILE);
        List<String> submissionLines = FileHandler.readAllLines(Config.SUBMISSIONS_FILE);

        // collect assigned submission IDs
        List<String> assignedIds = new ArrayList<>();
        for (String line : sessionLines) {
            String[] parts = line.split(Config.DELIMITER_READ, -1);
            if (parts.length >= 8 && parts[7].equals(evaluatorId)) {
                assignedIds.add(parts[8]); // student ID
            }
        }

        // load submissions matching IDs
        List<Submission> result = new ArrayList<>();
        for (String line : submissionLines) {
            Submission s = Submission.fromFileLine(line);
            if (s != null && assignedIds.contains(s.getStudentID())) {
                result.add(s);
            }
        }

        return result;
    }

    // Save or update evaluation in evaluations.txt
    public static void saveOrUpdateEvaluation(Evaluation newEval) {

        List<String> lines = FileHandler.readAllLines(Config.EVALUATIONS_FILE);
        List<String> updated = new ArrayList<>();

        boolean replaced = false;

        for (String line : lines) {
            Evaluation existing = Evaluation.fromFileLine(line);
            if (existing == null) continue;

            boolean sameKey = existing.getSubmissionId().equals(newEval.getSubmissionId())
                    && existing.getEvaluatorId().equals(newEval.getEvaluatorId());

            if (sameKey) {
                updated.add(newEval.toFileLine());
                replaced = true;
            } else {
                updated.add(line);
            }
        }

        if (!replaced) {
            updated.add(newEval.toFileLine());
        }

        FileHandler.overwriteAll(Config.EVALUATIONS_FILE, updated);
    }

    // Load existing evaluation (if evaluator already graded it)
    public static Evaluation getExistingEvaluation(String evaluatorId, String submissionId) {
        List<String> lines = FileHandler.readAllLines(Config.EVALUATIONS_FILE);

        for (String line : lines) {
            Evaluation e = Evaluation.fromFileLine(line);
            if (e == null) continue;

            if (e.getEvaluatorId().equals(evaluatorId) && e.getSubmissionId().equals(submissionId)) {
                return e;
            }
        }
        return null;
    }

    


}


