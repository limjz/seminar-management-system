package controller;

import model.Evaluation;
import model.Submission;
import utils.Config;
import utils.FileHandler;

import java.util.ArrayList;
import java.util.List;

public class EvaluationController {

    // 1) Get submissions assigned to evaluator (using assignments.txt)
    public static List<Submission> getAssignedSubmissions(String evaluatorId) {

        List<String> assignmentLines = FileHandler.readAllLines(Config.ASSIGNMENTS_FILE);
        List<String> submissionLines = FileHandler.readAllLines(Config.SUBMISSIONS_FILE);

        // collect assigned submission IDs
        List<String> assignedIds = new ArrayList<>();
        for (String line : assignmentLines) {
            String[] parts = line.split(Config.DELIMITER_READ_REGEX, -1);
            if (parts.length >= 2 && parts[0].equals(evaluatorId)) {
                assignedIds.add(parts[1]);
            }
        }

        // load submissions matching IDs
        List<Submission> result = new ArrayList<>();
        for (String line : submissionLines) {
            Submission s = Submission.fromFileLine(line);
            if (s != null && assignedIds.contains(s.getSubmissionId())) {
                result.add(s);
            }
        }

        return result;
    }

    // 2) Save or update evaluation in evaluations.txt
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

    // 3) Load existing evaluation (if evaluator already graded it)
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


