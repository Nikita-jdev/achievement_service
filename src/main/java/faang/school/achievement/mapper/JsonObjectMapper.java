package faang.school.achievement.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.exception.MappingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JsonObjectMapper {

    private final ObjectMapper objectMapper;

    public <T> T readValue(byte[] src, Class<T> valueType) {
        try {
            return objectMapper.readValue(src, valueType);
        } catch (IOException e) {
            log.error("IOException: ", e);
            throw new MappingException("Can't transform object into class: " + valueType.getName());
        }
    }

    public String writeValueAsString(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException exception) {
            log.error("JsonProcessingException: ", exception);
            throw new MappingException("Can't transform object into class: " + value.getClass().getName());
        }
    }
}
