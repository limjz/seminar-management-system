package views;

import controllers.ReportController;
import controllers.SeminarController;
import java.awt.*;
import java.util.List;
import javax.swing.*; 
import models.Seminar;
import utils.Config;


public class GenerateReportPage extends JFrame {

  private JComboBox<String> seminarBox; 
  private JTextArea displayArea;
  private ReportController reportController;
  private String seminarID; 
  private String seminarTitle;


  public GenerateReportPage()
  { 
    super ("Generate Reports & Schedule"); 
    setSize (Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT); 
    setLayout( new BorderLayout() );


    // ------- seminar selector drop down ---------
    JPanel topPanel = new JPanel (new FlowLayout( FlowLayout.LEFT));
     
    JLabel seminarLabel = new JLabel("Select Seminar Event:  ");
    seminarBox = new JComboBox<>(); 
    loadSeminar();
    


    // --------- paper sheets area for display of the schedule & evaluation result -----
    displayArea = new JTextArea(); 
    displayArea.setFont(new Font("Monospaced", Font.PLAIN, 14)); // Monospaced aligns text perfectly
    displayArea.setEditable(false);
    displayArea.setMargin(new Insets(10, 10, 10, 10)); // Padding


    // -------------- button --------------
    JPanel buttonPanel = new JPanel(); 

    JButton scheduleButton = new JButton("Generate Schedule");
    JButton reportButton = new JButton ("Generate Report"); 
    JButton exportButton = new JButton ("Export"); 
    JButton backButton = new JButton("Back");

    //-------------- Action Listener ------------ 
    scheduleButton.addActionListener(e-> { 
      
      seminarID = getSeminarID(); 
      seminarTitle = getSeminarTitle();
      
      if (seminarID != null)
      { 
        reportController = new ReportController(); 
        String text = reportController.getScheduleTextArea(seminarID, seminarTitle); 
        displayArea.setText(text);

        // Scroll to top
        displayArea.setCaretPosition(0);
      }
    });

    reportButton.addActionListener(e->{ 
      
      seminarID = getSeminarID(); 
      seminarTitle = getSeminarTitle();
      

      if (seminarID != null)
      { 
        String text = reportController.getEvalutionReportTextArea(seminarID, seminarTitle);
        displayArea.setText(text);
        displayArea.setCaretPosition(0);
      }
    });


    backButton.addActionListener(e-> { 
      dispose();
      Config.setCoordinatorDashboardVsible();
    });




    topPanel.add (seminarLabel); 
    topPanel.add( seminarBox); 
    

    buttonPanel.add(backButton);
    buttonPanel.add(scheduleButton); 
    buttonPanel.add(reportButton); 
    buttonPanel.add(exportButton); 
     

    add (topPanel, BorderLayout.NORTH); 
    add (new JScrollPane(displayArea), BorderLayout.CENTER);
    add(buttonPanel, BorderLayout.SOUTH);


    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    
  }


  private void loadSeminar () { 

    SeminarController sc = new SeminarController(); 
    List <Seminar> allSeminar = sc.getAllSeminars(); 

    seminarBox.removeAllItems();
    for (Seminar s : allSeminar )
    { 
      seminarBox.addItem(s.getSeminarID() + " - " + s.getSeminarTitle());
    }

  }

  private String getSeminarID () { 

    String rawSeminar = (String) seminarBox.getSelectedItem(); 
    if(rawSeminar == null) 
    {
      return null;
    }

    return rawSeminar.split(" - ")[0];
  }

  private String getSeminarTitle (){ 

    String rawSeminar = (String) seminarBox.getSelectedItem(); 
        if(rawSeminar == null) 
    {
      return null;
    }
    
    return rawSeminar.split(" - ")[1];
  }

}
