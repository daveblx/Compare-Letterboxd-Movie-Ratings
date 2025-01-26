import java.io.*;
import java.util.*;

public class MovieReader {

    public static List<Movie> readMoviesFromCSV(String filePath) {
        List<Movie> movies = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Read the header line (if present)
            br.readLine(); // Skipping header line if it exists

            while ((line = br.readLine()) != null) {
                String[] columns = parseCSVLine(line);

                if (columns.length == 5) {  // Ensure that there are exactly 5 columns
                    String date = columns[0].trim();
                    String name = columns[1].trim();
                    String yearStr = columns[2].trim();
                    String url = columns[3].trim();
                    String ratingStr = columns[4].trim();

                    // Validate and parse year and rating
                    try {
                        int year = Integer.parseInt(yearStr);
                        double rating = Double.parseDouble(ratingStr);

                        // Only add movie if the rating is valid
                        if (rating >= 0 && rating <= 10) {
                            movies.add(new Movie(name, year, rating));
                        } else {
                            System.out.println("Invalid rating for movie: " + name + ", skipping...");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid year or rating for movie: " + name + ", skipping...");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the CSV file: " + e.getMessage());
        }

        return movies;
    }

    // Manually parse a CSV line, handling commas inside quotes
    private static String[] parseCSVLine(String line) {
        List<String> result = new ArrayList<>();
        StringBuilder currentField = new StringBuilder();
        boolean insideQuotes = false;

        // Iterate through each character of the line
        for (int i = 0; i < line.length(); i++) {
            char currentChar = line.charAt(i);

            // Handle the case of entering or leaving quotes
            if (currentChar == '"') {
                if (insideQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    // If we have a double quote ("") inside a quoted string, treat it as a literal quote
                    currentField.append('"');
                    i++;  // Skip the next quote
                } else {
                    // Toggle the insideQuotes flag
                    insideQuotes = !insideQuotes;
                }
            } else if (currentChar == ',' && !insideQuotes) {
                // If we are not inside quotes and find a comma, treat it as a delimiter
                result.add(currentField.toString().trim());
                currentField.setLength(0);  // Clear the current field buffer
            } else {
                // Otherwise, just add the character to the current field
                currentField.append(currentChar);
            }
        }

        // Add the last field after the loop
        result.add(currentField.toString().trim());

        return result.toArray(new String[0]);
    }
}