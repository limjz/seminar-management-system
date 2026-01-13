package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
  
  public static List<String> readAllLines (String filepath)
  {
    List<String> lines = new ArrayList<>();
    File file = new File(filepath);

    if (!file.exists()) {
      System.out.println("File not found: " + filepath);
      return lines;
    }

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      String line;
      // Read each line and add to the list
      while ((line = br.readLine()) != null) {
        lines.add(line);
      }
    } catch (IOException e) {
      System.err.println("Error reading file: " + e.getMessage());
    }

    return lines;

  }
   
  // Append data to a file (add a new line)
  public static void appendData (String filepath, String data)
  {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath, true))) { // append = true meaning add a new line
      writer.write(data);
      writer.newLine();

    } catch (IOException e) {
      System.err.println("Error writing to file: " + e.getMessage());
    }
  }

  public static void updateData (String filepath, String targetID, String newData)
  { 
    List <String> allLines = readAllLines(filepath); 
    List <String> newLines = new ArrayList<>(); 
    boolean found = false;

    for (String line : allLines)
    { 
      String [] parts = line.split (Config.DELIMITER_READ); 
      if (parts[0].equals(targetID))
      {
        newLines.add(newData); // new data is the whole line, but not just the specific data object 
        found = true; 
      }
      else 
      {
        newLines.add(line); // keep the original data line
      }
    }

    if (found)
    { 
      // write the whole list into file 
      try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath, false))) { //append = false meaning rewrite the file
      for (String line : newLines)
      {
        writer.write(line);
        writer.newLine();
      }
      } catch (IOException e) {
        System.err.println("Error updating file: " + e.getMessage());
      }
    }
    else 
    { 
      System.out.println ("ID not found."); 
    }
  }
}
