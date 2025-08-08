import javax.swing.JFileChooser;           // For file selection dialog

import java.io.BufferedReader;             // For reading file line by line
import java.io.File;                       // To represent the selected file
import java.io.IOException;                // To handle file reading exceptions

import java.nio.file.Files;                // Provides static methods to read/write files
import java.nio.file.Path;                 // Represents a file path in a cross-platform way

public class FileScan {
    public static void main(String[] args) {
        File selectedFile = null;

        if (args.length == 0) {
            selectedFile = chooseFileWithDialog();
            if (selectedFile == null) {
                System.out.println("File selection cancelled.");
                return;
            }
        } else if (args.length == 1) {
            selectedFile = new File(args[0]);
            if (!selectedFile.exists() || !selectedFile.isFile()) {
                System.out.println("Error: File '" + args[0] + "' not found or is not a valid file.");
                return;
            }
        } else {
            System.out.println("Usage: java FileScan [filename]");
            return;
        }

        Path filePath = selectedFile.toPath();
        displayFileAndSummary(filePath, selectedFile);
    }

    // Updated name for clarity
    private static File chooseFileWithDialog() {
        JFileChooser chooser = new JFileChooser(new File("src"));
        int result = chooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        } else {
            return null; // User cancelled
        }
    }

    // Updated name for clarity
    private static void displayFileAndSummary(Path filePath, File selectedFile) {
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
            return; // Exit the method early
        }

        // Print the summary after reading is complete
        System.out.println("\n--- File Summary ---");
        System.out.println("File name: " + selectedFile.getName());
        System.out.println("Number of lines: " + lineCount);
        System.out.println("Number of words: " + wordCount);
        System.out.println("Number of characters: " + charCount);
    }
}
