package util;

import models.Request;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CsvFileHandler {


    private static final String FILE_LOCATION =
            "C:\\Users\\Toby Peel\\DEV\\Java Projects\\InterviewQuestionRestApi\\src\\main\\resources\\requests.csv";
    private static final String TEMP_FILE_LOCATION =
            "C:\\Users\\Toby Peel\\DEV\\Java Projects\\InterviewQuestionRestApi\\src\\main\\resources\\tempRequests.csv";
    private static final String SEPERATOR = ",";

    public static void writeCsvStringToFile(String csv) throws IOException {
        BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(FILE_LOCATION, true));
        writer.write(csv);
        writer.close();
    }

    public static String readFromCsv() throws IOException {
        StringBuilder csv = new StringBuilder();
        String line = "";
        BufferedReader reader = new BufferedReader(new FileReader(FILE_LOCATION));
        while ((line = reader.readLine()) != null) {
            csv.append(line).append(System.lineSeparator());
        }
        reader.close();
        return csv.toString();
    }

    public static boolean removeLineFromFile(String username) throws IOException {
        File tempFile = new File(TEMP_FILE_LOCATION);
        BufferedReader reader = new BufferedReader(new FileReader(new File(FILE_LOCATION)));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String line;
        while ((line = reader.readLine()) != null) {
            Request request = new Request().convertToObject(line.split(SEPERATOR));
            if (!(request.getUser().getUsername().equals(username))) {
                writer.write(line + System.lineSeparator());
            }
        }

        reader.close();
        writer.close();

        Files.deleteIfExists(Paths.get(FILE_LOCATION));
        tempFile.renameTo(new File(FILE_LOCATION));
        return true;

    }
}
