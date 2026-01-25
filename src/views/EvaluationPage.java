package views;

import controllers.EvaluationController;
import java.awt.*;
import java.io.File;
import java.net.URI;
import java.util.List;
import javax.swing.*;
import models.Evaluation;
import models.Submission;
import java.awt.Desktop;

public class EvaluationPage extends JFrame {

    // Evaluator ID passed from Login page (used to filter assigned submissions + save evaluation)
    private final String evaluatorId;

    // Left-side list (Assigned submissions)
    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final JList<String> listAssigned = new JList<>(listModel);

    // Top display info
    private final JLabel lblSelected = new JLabel("Selected: -");
    private final JLabel lblFileLink = new JLabel("<html>File: -</html>");

    // Rubric labels (change these depending on Oral/Poster)
    private final JLabel lbl1 = new JLabel("Problem Clarity (0-5):");
    private final JLabel lbl2 = new JLabel("Methodology (0-5):");
    private final JLabel lbl3 = new JLabel("Results (0-5):");
    private final JLabel lbl4 = new JLabel("Presentation (0-5):");

    // Rubric inputs (0 - 5)
    private final JSpinner sp1 = new JSpinner(new SpinnerNumberModel(0, 0, 5, 1));
    private final JSpinner sp2 = new JSpinner(new SpinnerNumberModel(0, 0, 5, 1));
    private final JSpinner sp3 = new JSpinner(new SpinnerNumberModel(0, 0, 5, 1));
    private final JSpinner sp4 = new JSpinner(new SpinnerNumberModel(0, 0, 5, 1));

    // Comments textbox
    private final JTextArea txtComments = new JTextArea(6, 30);

    // Buttons
    private final JButton btnOpenFile = new JButton("Open File");
    private final JButton btnClear = new JButton("Clear");
    private final JButton btnSave = new JButton("Save");

    // Data loaded from controller
    private List<Submission> assignedSubmissions;
    private Submission selected; // currently selected submission from list

    public EvaluationPage(String evaluatorId) {
        this.evaluatorId = evaluatorId;

        setTitle("Evaluation - " + evaluatorId);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(920, 560);
        setLocationRelativeTo(null);

        initLayout();
        loadAssigned();
        initEvents();
    }

    /**
     * Build UI:
     * - Left: list of assigned submissions
     * - Center: rubric + comments
     * - Bottom: action buttons
     */
    private void initLayout() {
        setLayout(new BorderLayout(10, 10));

        // LEFT panel: assigned submissions list
        JPanel left = new JPanel(new BorderLayout(5, 5));
        left.setPreferredSize(new Dimension(360, 0));
        left.setBorder(BorderFactory.createTitledBorder("Assigned Submissions"));
        left.add(new JScrollPane(listAssigned), BorderLayout.CENTER);

        // CENTER panel: evaluation rubric form
        JPanel center = new JPanel(new BorderLayout(10, 10));
        center.setBorder(BorderFactory.createTitledBorder("Rubric Evaluation"));

        // Top info: selected submission + file link
        JPanel topInfo = new JPanel(new GridLayout(2, 1));
        topInfo.add(lblSelected);
        topInfo.add(lblFileLink);
        center.add(topInfo, BorderLayout.NORTH);

        // Rubric form (labels + spinners)
        JPanel form = new JPanel(new GridLayout(4, 2, 10, 10));
        form.add(lbl1); form.add(sp1);
        form.add(lbl2); form.add(sp2);
        form.add(lbl3); form.add(sp3);
        form.add(lbl4); form.add(sp4);

        // Comments panel
        JPanel commentsPanel = new JPanel(new BorderLayout(5, 5));
        commentsPanel.add(new JLabel("Comments:"), BorderLayout.NORTH);
        commentsPanel.add(new JScrollPane(txtComments), BorderLayout.CENTER);

        center.add(form, BorderLayout.CENTER);
        center.add(commentsPanel, BorderLayout.SOUTH);

        // BOTTOM panel: buttons
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnOpenFile.setEnabled(false); // only enable when a submission with file path is selected
        bottom.add(btnOpenFile);
        bottom.add(btnClear);
        bottom.add(btnSave);

        // Button actions
        btnClear.addActionListener(e -> clearForm());
        btnSave.addActionListener(e -> save());
        btnOpenFile.addActionListener(e -> openSelectedFile());

        add(left, BorderLayout.WEST);
        add(center, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }

    /**
     * Load assigned submissions for this evaluator.
     * Controller reads from txt file and returns the list.
     */
    private void loadAssigned() {
        assignedSubmissions = EvaluationController.getAssignedSubmissions(evaluatorId);
        listModel.clear();

        for (Submission s : assignedSubmissions) {
            listModel.addElement(s.getSubmissionId() + " - " + s.getTitle() + " (" + s.getType() + ")");
        }
    }

    /**
     * When user selects a submission:
     * 1) Show submission info (ID/title/type/file)
     * 2) Apply Oral/Poster rubric labels
     * 3) Load existing evaluation (if saved before) so it won't reset
     */
    private void initEvents() {
        listAssigned.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;

            int idx = listAssigned.getSelectedIndex();
            if (idx < 0 || idx >= assignedSubmissions.size()) return;

            selected = assignedSubmissions.get(idx);

            // Show selected submission info
            lblSelected.setText("Selected: " + selected.getSubmissionId()
                    + " | " + selected.getTitle()
                    + " | Type: " + selected.getType());

            // ✅ Apply different rubric labels depending on submission type (Oral/Poster)
            applyRubricLabels(selected.getType());

            // Show file path as hyperlink-style text and enable open button
            String filePath = selected.getFilePath();
            if (filePath == null || filePath.trim().isEmpty() || filePath.equals("-")) {
                lblFileLink.setText("<html>File: -</html>");
                btnOpenFile.setEnabled(false);
            } else {
                lblFileLink.setText("<html>File: <a href=''>" + filePath + "</a></html>");
                btnOpenFile.setEnabled(true);
            }

            // Load existing evaluation if any
            Evaluation existing = EvaluationController.getExistingEvaluation(evaluatorId, selected.getSubmissionId());
            if (existing != null) {
                // These are the numeric scores saved in txt
                sp1.setValue(existing.getProblemClarity());
                sp2.setValue(existing.getMethodology());
                sp3.setValue(existing.getResults());
                sp4.setValue(existing.getPresentation());
                txtComments.setText(existing.getComments());
            } else {
                clearForm();
            }
        });
    }

    /**
     * ✅ Different rubrics for Oral vs Poster.
     * We reuse the same 4 spinners but change the labels.
     * (This avoids changing your controller/model storage format too much.)
     */
    private void applyRubricLabels(String type) {
        if (type == null) type = "Oral";
        type = type.trim();

        if (type.equalsIgnoreCase("Poster")) {
            // Poster rubric (example criteria)
            lbl1.setText("Content Quality (0-5):");
            lbl2.setText("Visual Design (0-5):");
            lbl3.setText("Organization (0-5):");
            lbl4.setText("Explanation / Q&A (0-5):");
        } else {
            // Oral rubric (default)
            lbl1.setText("Problem Clarity (0-5):");
            lbl2.setText("Methodology (0-5):");
            lbl3.setText("Results (0-5):");
            lbl4.setText("Presentation (0-5):");
        }
    }

    /**
     * Reset rubric inputs and comments (does not delete saved txt records).
     */
    private void clearForm() {
        sp1.setValue(0);
        sp2.setValue(0);
        sp3.setValue(0);
        sp4.setValue(0);
        txtComments.setText("");
    }

    /**
     * Save evaluation to txt file via controller.
     * Controller will save OR update an existing record (same evaluatorId + submissionId).
     */
    private void save() {
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Please select a submission first.");
            return;
        }

        int score1 = (Integer) sp1.getValue();
        int score2 = (Integer) sp2.getValue();
        int score3 = (Integer) sp3.getValue();
        int score4 = (Integer) sp4.getValue();

        String comments = txtComments.getText().trim();
        if (comments.isEmpty()) comments = "-";

        // IMPORTANT: Evaluation constructor order is (evaluatorId, submissionId, ...)
        Evaluation ev = new Evaluation(
                evaluatorId,
                selected.getSubmissionId(),
                score1, score2, score3, score4,
                comments
        );

        EvaluationController.saveOrUpdateEvaluation(ev);

        JOptionPane.showMessageDialog(this,
                "Saved!\nTotal Score = " + ev.getTotalScore());
    }

    /**
     * Open the selected submission file (png/pptx/pdf/etc).
     * - If local file exists: open using default application
     * - Otherwise: try open as URL in browser
     */
    private void openSelectedFile() {
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Please select a submission first.");
            return;
        }

        String path = selected.getFilePath();
        if (path == null || path.trim().isEmpty() || path.equals("-")) {
            JOptionPane.showMessageDialog(this, "No file path available for this submission.");
            return;
        }

        try {
            // Try local file first
            File f = new File(path);
            if (f.exists()) {
                Desktop.getDesktop().open(f);
                return;
            }

            // If not a local file, try as URL
            Desktop.getDesktop().browse(new URI(path));

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Cannot open file/link.\n" + ex.getMessage());
        }
    }
}
