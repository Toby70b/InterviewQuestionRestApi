package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public class CsvConverter {


    public static <T> String convertObjectToCSV(T object) throws JsonProcessingException {
        CsvMapper mapper = new CsvMapper();
        mapper.findAndRegisterModules();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        CsvSchema schema = mapper.schemaFor(object.getClass());
        return mapper.writer(schema.withUseHeader(false)).writeValueAsString(object);
    }


}
