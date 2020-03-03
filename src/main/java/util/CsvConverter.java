package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public interface CsvConverter<T> {
    static final String SEPERATOR = ",";

    public String convertToCsv();
    public T convertToObject(String csv);
}
