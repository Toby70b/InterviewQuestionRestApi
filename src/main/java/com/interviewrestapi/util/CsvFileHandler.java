package com.interviewrestapi.util;

import com.interviewrestapi.model.Request;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CsvFileHandler {
    private static final String FILE_LOCATION = ".\\src\\main\\resources\\requests.csv";
    private static final String TEMP_FILE_LOCATION = ".\\src\\main\\resources\\tempRequests.csv";
    private static final String SEPERATOR = ",";

    public void writeCsvStringToFile(String csv) throws IOException {
        try(BufferedWriter bw = new BufferedWriter(new java.io.FileWriter(FILE_LOCATION, true))){
            bw.write(csv);
        }
        catch (IOException e){
            throw new IOException(e.getMessage());
        }
    }

    public String readFromCsv() throws IOException {
        StringBuilder csv = new StringBuilder();
        String line = "";
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_LOCATION))) {
            while ((line = br.readLine()) != null) {
                csv.append(line).append(System.lineSeparator());
            }
        }
        catch (IOException e){
            throw new IOException(e.getMessage());
        }
        return csv.toString();
    }

    public boolean removeLineFromFile(String username) throws IOException {
        File tempFile = new File(TEMP_FILE_LOCATION);
        BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_LOCATION))) {
            while ((line = br.readLine()) != null) {
                Request request = new Request().convertToObject(line.split(SEPERATOR));
                if (!(request.getUser().getUsername().equals(username))) {
                    bw.write(line + System.lineSeparator());
                }
            }
        }
        catch (IOException e) {
            bw.close();
            throw new IOException(e.getMessage());
        }

        Files.deleteIfExists(Paths.get(FILE_LOCATION));
        tempFile.renameTo(new File(FILE_LOCATION));
        return true;

    }
}
