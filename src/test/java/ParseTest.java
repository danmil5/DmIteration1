import java.io.*;
import java.util.Arrays;
import com.opencsv.CSVReader;

public class ParseTest {
    public static void main(String[] args) throws IOException {
        // Create new CSVReader that reads in between commas
        CSVReader reader = new CSVReader(new FileReader("src/main/resources/SEOExample.csv"), ',', '"', 0);
        // Create an array to hold the rows from the csv file before they get printed
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            if (nextLine != null) {
                for (int ColumnCount = 0; 10 > ColumnCount; ColumnCount++) {
                    // Change value to <blank> if left blank rather than leave open spaces in between commas
                    if (nextLine[ColumnCount].length() == 0) {
                        nextLine[ColumnCount] = "<Blank>";
                    }
                }
                // Print each line one at a time after altering blank spaces here
                System.out.println(Arrays.toString(nextLine));
            }
        }

    }
}
