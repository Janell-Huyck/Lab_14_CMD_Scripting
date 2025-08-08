import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class DataSaver {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<String> records = new ArrayList<>();
        int recordCount = 0;

        System.out.println("Welcome to DataSaver!");

        do {
            String firstName = SafeInput.getNonZeroLenString(in, "Enter first name");
            String lastName = SafeInput.getNonZeroLenString(in, "Enter last name");

            // increment the count of the records
            recordCount++;

            // ID Number formatted to 6-digit string with leading zeroes
            String idNumber = String.format("%06d", recordCount);

            String email = SafeInput.getNonZeroLenString(in, "Enter email");
            int yearOfBirth = SafeInput.getRangedInt(in, "Enter year of birth", 1900, 2025);

            // Build CSV record: First, Last, ID, Email, BirthYear
            String csvRecord = String.join(", ",
                    firstName,
                    lastName,
                    idNumber,
                    email,
                    Integer.toString(yearOfBirth));

            // Add record to list
            records.add(csvRecord);

        } while (SafeInput.getYNConfirm(in, "Do you want to add another record?"));

        // Ask for file name and add .csv extension if needed
        String fileName = SafeInput.getNonZeroLenString(in, "Enter file name to save (no extension)");
        if (!fileName.endsWith(".csv")) {
            fileName += ".csv";
        }

        // Write to file in src directory
        try (FileWriter writer = new FileWriter(Paths.get("src", fileName).toFile())) {
            for (String record : records) {
                writer.write(record + System.lineSeparator());
            }
            System.out.println("Data saved to " + fileName + " in the src directory.");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }

        in.close();
    }
}