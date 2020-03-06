package util;

/*
   Note: Made this and interface to allow classes to define how to convert to and from a CSV themselves.
   However I could make this an abstract class if there was a default mode of behaviour to converting to and from
*/

import java.io.IOException;

public interface CsvConverter<T> {
    String SEPERATOR = ",";

    String convertToCsv();

    T convertToObject(String[] properties) throws IOException;
}
