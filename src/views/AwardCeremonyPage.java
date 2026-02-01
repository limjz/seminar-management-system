package views;

import controllers.AwardController;
import controllers.SeminarController;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import models.Seminar;
import models.Winner;
import utils.Config;

public class AwardCeremonyPage extends JFrame {

    private JComboBox<String> seminarBox;
    private JTextArea displayArea;
    private AwardController awardController;

    public AwardCeremonyPage() {
        super("Award Ceremony & Agenda");
        setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        setLayout(new BorderLayout());
        
        awardController = new AwardController();

        //------ Select Seminar Drop Down ------
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Select Seminar Event:  "));
        seminarBox = new JComboBox<>();
        loadSeminars();
        top.add(seminarBox);
        add(top, BorderLayout.NORTH);

        //-----------  DisplayText Area ---------
        displayArea = new JTextArea();
        displayArea.setFont(new Font("Monospaced", Font.BOLD, 14));
        displayArea.setEditable(false);
        displayArea.setMargin(new Insets(20, 20, 20, 20));
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        //----------- Button --------------
        JPanel bot = new JPanel();
        JButton generateButton = new JButton("Generate Agenda");
        JButton closeButton = new JButton("Close");

        //----------- Action Listener -------------
        generateButton.addActionListener(e -> generateAgenda());
        closeButton.addActionListener(e -> dispose());

        bot.add(generateButton);
        bot.add(closeButton);
        add(bot, BorderLayout.SOUTH);
        
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void loadSeminars() {
        SeminarController sc = new SeminarController();
        List<Seminar> list = sc.getAllSeminars();
        seminarBox.removeAllItems();
        for (Seminar s : list) {
            seminarBox.addItem(s.getSeminarID() + " - " + s.getSeminarTitle());
        }
    }

    private void generateAgenda() {
        String raw = (String) seminarBox.getSelectedItem();
        if (raw == null) return; //no seminar in the drop down
        
        String seminarId = raw.split(" - ")[0].trim();
        String seminarTitle = raw.split(" - ")[1].trim();

        StringBuilder sb = new StringBuilder();
        sb.append("**********************************************\n");
        sb.append("        AWARD CEREMONY AGENDA\n");
        sb.append("  Seminar Title: ").append(seminarTitle).append("\n");
        sb.append("**********************************************\n\n");

        sb.append("1. Opening Speech\n");
        sb.append("2. Review of Presentations\n\n");
        sb.append("3. ANNOUNCEMENT OF WINNERS:\n\n");

        // --- BEST ORAL ---
        Winner oralWin = awardController.getBestPresenter(seminarId, "Oral");
        sb.append("   [ BEST ORAL PRESENTATION ]\n");
        if (oralWin != null) {
            sb.append("   WINNER: ").append(oralWin.getStudentID()).append("\n");
            sb.append("   TITLE:  ").append(oralWin.getTitle()).append("\n");
            sb.append("   SCORE:  ").append((int)oralWin.getTotalScore()).append(" / 100\n");
        } else {
            sb.append("   (No eligible presenter found)\n");
        }
        sb.append("   -----------------------------------\n\n");

        // --- BEST POSTER ---
        Winner posterWin = awardController.getBestPresenter(seminarId, "Poster");
        sb.append("   [ BEST POSTER PRESENTATION ]\n");
        if (posterWin != null) {
            sb.append("   WINNER: ").append(posterWin.getStudentID()).append("\n");
            sb.append("   TITLE:  ").append(posterWin.getTitle()).append("\n");
            sb.append("   SCORE:  ").append((int)posterWin.getTotalScore()).append(" / 100\n");
        } else {
            sb.append("   (No eligible presenter found)\n");
        }
        sb.append("   -----------------------------------\n\n");

        // --- PEOPLE'S CHOICE (Single Winner) ---
        Winner peopleWin = awardController.getPeopleChoice(seminarId);
        sb.append("   [ PEOPLE'S CHOICE AWARD ]\n");
        sb.append("   (Criteria: Highest Presentation Skills Rating)\n");
        
        if (peopleWin != null) {
            sb.append("   WINNER: ").append(peopleWin.getStudentID()).append("\n");
            sb.append("   TITLE:  ").append(peopleWin.getTitle()).append("\n");
            sb.append("   SCORE: ").append((int)peopleWin.getPresentationScore()).append(" / 25\n");
            //sb.append("   (Tie-Break Score: ").append((int)peopleWin.getTotalScore()).append(")\n");
        } else {
            sb.append("   (No eligible evaluations found)\n");
        }
        
        sb.append("\n**************************************************\n");
        sb.append("4. Closing Ceremony\n");
        
        displayArea.setText(sb.toString());
        displayArea.setCaretPosition(0); 
    }
}