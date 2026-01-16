// This corresponds to the "StudentController" in your Controller Layer
public class StudentController {

    // Method defined in your diagram: + getAvailableSession()
    public String[] getAvailableSession() {
        // TODO: Read from session.txt via FileHandler
        // Returning dummy data for now so the UI works
        return new String[]{"Session A - 10:00 AM", "Session B - 2:00 PM"};
    }

    // Method defined in your diagram: + registerPresentation(title, type, sessionID)
    public boolean registerPresentation(String title, String type, String sessionID) {
        // TODO: Create a new Presentation object
        // TODO: Save to presentation.txt via FileHandler
        
        System.out.println("Registering: " + title + " (" + type + ") for " + sessionID);
        return true; // Return true if save was successful
    }

    // Method defined in your diagram: + uploadMaterial(presID)
    // Note: I modified params slightly to take the file path
    public boolean uploadMaterial(String studentID, String filePath) {
        // TODO: Locate the presentation for this student
        // TODO: Update the record with the file path
        
        System.out.println("Uploading file: " + filePath + " for student " + studentID);
        return true;
    }
}