package statModule.api;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class WriteToCsv {
    public static void writeToCSV(TreeMap<String, Object> all, String filename) throws IOException {
        String fullpath = "/home/fellixlyu1/IdeaProjects/nba-stats/csv_files/" + filename;
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fullpath))) {

            for (Map.Entry<String, Object> entry : all.entrySet()) {
                bufferedWriter.write(entry.getKey() + ", " + entry.getValue());
                bufferedWriter.newLine();
            }

            System.out.println("CSV file successfully created");
        } catch (IOException e) {
            System.out.println("Error writing to csv " + e.getMessage());
        }
    }
}
