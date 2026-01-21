package views;

import controllers.EvaluationController;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import models.Evaluation;
import models.Submission;

public class EvaluationPage extends JFrame {

    private final String evaluatorId;

    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final JList<String> listAssigned = new JList<>(listModel);

    private final JLabel lblSelected = new JLabel("Selected: -");

    private final JSpinner spProblem = new JSpinner(new SpinnerNumberModel(0, 0, 5, 1));
    private final JSpinner spMethod  = new JSpinner(new SpinnerNumberModel(0, 0, 5, 1));
    private final JSpinner spResults = new JSpinner(new SpinnerNumberModel(0, 0, 5, 1));
    private final JSpinner spPresent = new JSpinner(new SpinnerNumberModel(0, 0, 5, 1));

    private final JTextArea txtComments = new JTextArea(6, 30);

    private List<Submission> assignedSubmissions;
    private Submission selected;

    public EvaluationPage(String evaluatorId) {
        this.evaluatorId = evaluatorId;

        setTitle("Evaluation - " + evaluatorId);
        setSize(900, 520);
        
        initLayout();
        loadAssigned();
        initEvents();

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initLayout() {
        setLayout(new BorderLayout(10, 10));

        // LEFT: assigned submissions
        JPanel left = new JPanel(new BorderLayout(5, 5));
        left.setPreferredSize(new Dimension(360, 0));
        left.setBorder(BorderFactory.createTitledBorder("Assigned Submissions"));
        left.add(new JScrollPane(listAssigned), BorderLayout.CENTER);

        // CENTER: rubric form
        JPanel center = new JPanel(new BorderLayout(10, 10));
        center.setBorder(BorderFactory.createTitledBorder("Rubric Evaluation"));

        JPanel form = new JPanel(new GridLayout(4, 2, 10, 10));
        form.add(new JLabel("Problem Clarity (0-5):"));
        form.add(spProblem);

        form.add(new JLabel("Methodology (0-5):"));
        form.add(spMethod);

        form.add(new JLabel("Results (0-5):"));
        form.add(spResults);

        form.add(new JLabel("Presentation (0-5):"));
        form.add(spPresent);

        JPanel commentsPanel = new JPanel(new BorderLayout(5, 5));
        commentsPanel.add(new JLabel("Comments:"), BorderLayout.NORTH);
        commentsPanel.add(new JScrollPane(txtComments), BorderLayout.CENTER);

        center.add(lblSelected, BorderLayout.NORTH);
        center.add(form, BorderLayout.CENTER);
        center.add(commentsPanel, BorderLayout.SOUTH);

        // BOTTOM: buttons
        JButton btnClear = new JButton("Clear");
        JButton btnSave = new JButton("Save");

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(btnClear);
        bottom.add(btnSave);

        btnClear.addActionListener(e -> clearForm());
        btnSave.addActionListener(e -> save());

        add(left, BorderLayout.WEST);
        add(center, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }

    private void loadAssigned() {
        assignedSubmissions = EvaluationController.getAssignedSubmissions(evaluatorId);
        listModel.clear();

        for (Submission s : assignedSubmissions) {
            listModel.addElement(s.getSubmissionID() + " - " + s.getTitle() + " (" + s.getType() + ")");
        }
    }

    private void initEvents() {
        listAssigned.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;

            int idx = listAssigned.getSelectedIndex();
            if (idx < 0 || idx >= assignedSubmissions.size()) return;

            selected = assignedSubmissions.get(idx);

            lblSelected.setText("Selected: " + selected.getSubmissionID()
                    + " | " + selected.getTitle()
                    + " | File: " + selected.getMaterial());

            // load existing evaluation if any
            Evaluation existing = EvaluationController.getExistingEvaluation(evaluatorId, selected.getSubmissionID());
            if (existing != null) {
                spProblem.setValue(existing.getProblemClarity());
                spMethod.setValue(existing.getMethodology());
                spResults.setValue(existing.getResults());
                spPresent.setValue(existing.getPresentation());
                txtComments.setText(existing.getComments());
            } else {
                clearForm();
            }
        });
    }

    private void clearForm() {
        spProblem.setValue(0);
        spMethod.setValue(0);
        spResults.setValue(0);
        spPresent.setValue(0);
        txtComments.setText("");
    }



    private void save() {
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Please select a submission first.");
            return;
        }

        int problem = (Integer) spProblem.getValue();
        int method  = (Integer) spMethod.getValue();
        int results = (Integer) spResults.getValue();
        int present = (Integer) spPresent.getValue();
        String comments = txtComments.getText().trim();
        int totalScore = problem + method + results + present;

        if (comments.isEmpty()) comments = "-";

        Evaluation ev = new Evaluation(
                evaluatorId,
                selected.getSubmissionID(),
                selected.getStudentID(),
                problem, method, results, present,
                comments,
                totalScore
        );

        EvaluationController.saveOrUpdateEvaluation(ev);

        JOptionPane.showMessageDialog(this,
                "Saved!\nTotal Score = " + ev.getTotalScore());
    }
}
