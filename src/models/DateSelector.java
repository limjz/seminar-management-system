package models;

import java.awt.*;
import java.time.*;
import javax.swing.*;


public class DateSelector extends JPanel {

  private final JComboBox<Integer> dayBox; 
  private final JComboBox<String> monthBox; 
  private final JComboBox<Integer> yearBox;

  public DateSelector() {
    setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
    
    yearBox = new JComboBox<>(); 
    int currentYear = LocalDate.now().getYear();
    for (int i = 0; i < 5; i++){ 
      yearBox.addItem(currentYear + i);
    }

    String[] months = { "Jan" , "Feb" , "Mar" , "Apr" , "May" , "Jun" , "Jul" , "Aug" , "Sep" , "Oct" , "Nov" , "Dec" };
    monthBox = new JComboBox<>(months);

    dayBox = new JComboBox<>();
    updateDays();


    add(dayBox); 
    add(monthBox);
    add(yearBox);

  }

  private void updateDays() { 
    
    int year = (int) yearBox.getSelectedItem(); 
    int month = monthBox.getSelectedIndex() + 1; // Jan is 0 in the array 

    YearMonth yearMonth = YearMonth.of(year, month);  
    int daysInMonth = yearMonth.lengthOfMonth();

    dayBox.removeAllItems(); 
    for (int i = 1; i <= daysInMonth; i++) {
      dayBox.addItem(i);
    }

  }
  // get selected date format YYYY-MM-DD
  public String getSelectedDate_String() { 
    int day = (int) dayBox.getSelectedItem(); 
    int month = monthBox.getSelectedIndex() + 1; 
    int year = (int) yearBox.getSelectedItem(); 

    return String.format("%02d-%02d-%02d", day, month, year);
  }


}
