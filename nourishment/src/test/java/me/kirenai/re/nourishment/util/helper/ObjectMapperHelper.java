package me.kirenai.re.nourishment.util.helper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class ObjectMapperHelper {

    private final ObjectMapper mapper = new ObjectMapper();

    public <T> T readValue(InputStream src, Class<T> valueType) throws IOException {
        return this.mapper.readValue(src, valueType);
    }


    public <T> T readValue(InputStream src, TypeReference<T> valueType) throws IOException {
        return this.mapper.readValue(src, valueType);
    }

}
