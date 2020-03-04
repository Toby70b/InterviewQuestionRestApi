package util;

import models.Request;

import java.io.*;

public class CsvFileWriter {
    private static final String FILE_LOCATION =
            "C:\\Users\\Toby Peel\\DEV\\Java Projects\\InterviewQuestionRestApi\\src\\main\\resources\\requests.csv";
    private static final String SEPERATOR = ",";

    public static void writeCsvStringToFile(String csv) throws IOException {
        BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(FILE_LOCATION,true));
        writer.write(csv);
        writer.close();
    }

    public static String readFromCsv() throws IOException {
        StringBuilder csv = new StringBuilder();
        String line = "";
        BufferedReader br = new BufferedReader(new FileReader(FILE_LOCATION));
            while ((line = br.readLine()) != null) {
                csv.append(line).append("\n");
            }

        return csv.toString();
    }
}
