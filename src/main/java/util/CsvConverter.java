package util;

/*
   Note: Made this and interface to allow classes to define how to convert to and from a CSV themselves.
   However I could make this an abstract class if there was a default mode of behaviour to converting to and from
*/

public interface CsvConverter<T> {
    static final String SEPERATOR = ",";

    public String convertToCsv();
    public T convertToObject(String[] properties);
}
