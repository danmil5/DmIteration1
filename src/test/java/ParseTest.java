import java.io.*;
import java.sql.*;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.opencsv.CSVReader;

public class ParseTest {
    public static void main(String[] args) throws IOException {
        String sql;
        Statement stmt = null;
        PreparedStatement ps;
        Connection conn = null;
        String checkEmail;
        String checkUrl;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException cn) {
            cn.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/sqliteTest", "", "");
            stmt = conn.createStatement();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        Gson gson = new Gson();
        JsonReader jread = new JsonReader(new FileReader("C:\\Users\\danmi\\IdeaProjects\\DmIteration1\\src\\main\\resources\\authors.json"));
        AuthorParser[] authors = gson.fromJson(jread, AuthorParser[].class);
        for (AuthorParser element : authors) {
            sql = "INSERT INTO author (author_name, author_email, author_url) VALUES (?, ?, ?)";
            try {
                ps = conn.prepareStatement(sql);
                ps.setString(1, element.getName());
                if (element.getEmail().length() == 0) {
                    checkEmail = null;
                }
                else {
                    checkEmail = element.getEmail();
                }
                ps.setString(2, checkEmail);
                if (element.getUrl().length() == 0) {
                    checkUrl = null;
                }
                else {
                    checkUrl = element.getUrl();
                }
                ps.setString(3, checkUrl);
                ps.executeUpdate();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        // Create new CSVReader that reads in between commas
        CSVReader reader = new CSVReader(new FileReader("src/main/resources/bookstore_report2.csv"), ',', '"', 1);
        // Create an array to hold the rows from the csv file before they get printed
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            if (nextLine != null) {
                for (int ColumnCount = 0; 6 > ColumnCount; ColumnCount++) {
                    // Change value to null if left blank
                    if (nextLine[ColumnCount].length() == 0) {
                        nextLine[ColumnCount] = null;
                    }
                }
                    sql = "INSERT INTO book (isbn, publisher_name, author_name, book_title) VALUES (?, ?, ?, ?)";
                    try {
                        ps = conn.prepareStatement(sql);
                        ps.setString(1, nextLine[0]);
                        ps.setString(2, nextLine[3]);
                        ps.setString(3, nextLine[2]);
                        ps.setString(4, nextLine[1]);
                        ps.executeUpdate();
                    } catch (SQLException se) {
                        se.printStackTrace();
                    }
            }
        }

    }
}
