import javax.swing.JFileChooser;           // For file selection dialog

import java.io.BufferedReader;             // For reading file line by line
import java.io.File;                       // To represent the selected file
import java.io.IOException;                // To handle file reading exceptions

import java.nio.file.Files;                // Provides static methods to read/write files
import java.nio.file.Path;                 // Represents a file path in a cross-platform way

public class FileInspector {
    public static void main(String[] args) {
        // Open a file chooser that starts in the 'src' directory
        JFileChooser chooser = new JFileChooser(new File("src"));

        // Show the dialog and wait for the user's selection
        int result = chooser.showOpenDialog(null);

        // Only proceed if the user clicked the "Open" button
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();   // Get the file the user selected
            Path filePath = selectedFile.toPath();           // Convert File to NIO Path

            int lineCount = 0;
            int wordCount = 0;
            int charCount = 0;

            System.out.println("File Content:\n");

            // Try-with-resources: automatically closes the file reader
            try (BufferedReader reader = Files.newBufferedReader(filePath)) {
                String line;

                // Read each line one by one until we hit the end of the file
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);                // Echo the line to the screen
                    lineCount++;                             // Count this line

                    // Trim whitespace and split line by whitespace (\\s+)
                    // If the line is not empty, count its words
                    wordCount += line.trim().isEmpty() ? 0 : line.trim().split("\\s+").length;

                    // Count the number of characters (including spaces, but not newline)
                    charCount += line.length();
                }
            } catch (IOException e) {
                // Print error message if file reading fails
                System.err.println("Error reading file: " + e.getMessage());
                return; // Exit the program early
            }

            // Print the summary after reading is complete
            System.out.println("\n--- File Summary ---");
            System.out.println("File name: " + selectedFile.getName());
            System.out.println("Number of lines: " + lineCount);
            System.out.println("Number of words: " + wordCount);
            System.out.println("Number of characters: " + charCount);
        } else {
            // User canceled the file chooser dialog
            System.out.println("File selection cancelled.");
        }
    }
}