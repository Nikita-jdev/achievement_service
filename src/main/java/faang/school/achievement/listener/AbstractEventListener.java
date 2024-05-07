package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.MessageListener;

import java.io.IOException;

@RequiredArgsConstructor
public abstract class AbstractEventListener<T> implements MessageListener {
    private final ObjectMapper objectMapper;

    protected T convert(byte[] body,Class<T> type) {
        try {
            return objectMapper.readValue(body, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
