package controllers;

import java.util.ArrayList;
import java.util.List;
import utils.Config;
import utils.FileHandler;

public class StudentController {

    // 1. Get Available Sessions (Read from sessions.txt)
    public String[] getAvailableSession() {
        List<String> lines = FileHandler.readAllLines(Config.SESSIONS_FILE);
        List<String> sessionList = new ArrayList<>();

        for (String line : lines) {
            String[] parts = line.split(Config.DELIMITER_READ);
            if (parts.length >= 2) {
                sessionList.add(parts[0] + " | " + parts[1]);
            }
        }
        return sessionList.toArray(new String[0]);
    }

    // 2. Register Presentation (Write to presentation.txt)
    public boolean registerPresentation(String studentID, String title, String type, String abstractText, String supervisor, String sessionInfo) {
        
    List<String> lines = FileHandler.readAllLines(Config.PRESENTATION_FILE);
        int nextId = 1; // Default starting ID

        if (!lines.isEmpty()) {
            try {
                // Get the last line in the file
                String lastLine = lines.get(lines.size() - 1);
                String[] parts = lastLine.split(Config.DELIMITER_READ);
                
                // Assuming ID is the first column (index 0)
                String lastIdString = parts[0]; // e.g., "SES-005"
                
                // Remove "SES-" prefix and parse the number
                String numberPart = lastIdString.replace("SES-", "");
                nextId = Integer.parseInt(numberPart) + 1;
            } catch (Exception e) {
                // If there's an error parsing (e.g. file is corrupted), reset to 1
                System.out.println("Error parsing last ID, resetting to 1.");
                nextId = 1;
            }
        }

        // Format the new ID with leading zeros (e.g., SES-006)
        String presID = String.format("SES-%03d", nextId);
        
        // Extract just the SessionID from the dropdown string (e.g., "SESSION-01 | IPv4" -> "SESSION-01")
        String sessionID = sessionInfo.split(" \\| ")[0];

        // Prepare the data line
        // Format: PresID|StudentID|Title|Abstract|Supervisor|Type|SessionID|Material
        String newRecord = presID + Config.DELIMITER_WRITE +
                           studentID + Config.DELIMITER_WRITE +
                           title + Config.DELIMITER_WRITE +
                           abstractText + Config.DELIMITER_WRITE +
                           supervisor + Config.DELIMITER_WRITE +
                           type + Config.DELIMITER_WRITE +
                           sessionID + Config.DELIMITER_WRITE +
                           "null"; //Material 

        FileHandler.appendData(Config.PRESENTATION_FILE, newRecord);
        return true;
    }

    // 3. Upload Material (Update presentation.txt)
// 3. Upload Material (Updated to filter by Session)
    public boolean uploadMaterial(String studentID, String sessionInfo, String filePath) {
        List<String> lines = FileHandler.readAllLines(Config.PRESENTATION_FILE);
        String targetPresID = null;
        String currentLineData = null;

        // Extract "SESSION-01" from "SESSION-01 | IPv4"
        String targetSessionID = sessionInfo.split(" \\| ")[0];

        // Step 1: Find the registration record matching StudentID AND SessionID
        for (String line : lines) {
            String[] parts = line.split(Config.DELIMITER_READ);
            
            // Validation: Ensure line has enough columns
            if (parts.length < 8) continue; 

            String currentStudentID = parts[1];
            String currentSessionID = parts[6]; // SessionID is at index 6

            if (currentStudentID.equals(studentID) && currentSessionID.equals(targetSessionID)) {
                targetPresID = parts[0]; // Capture the Presentation ID
                currentLineData = line;
                break;
            }
        }

        // Step 2: If found, update the file path
        if (targetPresID != null) {
            String[] parts = currentLineData.split(Config.DELIMITER_READ);
            
            // Rebuild the line
            StringBuilder newLine = new StringBuilder();
            // Append the first 7 fields (PresID to SessionID)
            for (int i = 0; i < 6; i++) {
                if (i < parts.length) newLine.append(parts[i]);
                newLine.append(Config.DELIMITER_WRITE);
            }
            newLine.append(filePath).append(Config.DELIMITER_WRITE);


            // Update the file
            FileHandler.updateData(Config.PRESENTATION_FILE, targetPresID, newLine.toString());
            return true;
        }

        return false; // No matching registration found
    }
}