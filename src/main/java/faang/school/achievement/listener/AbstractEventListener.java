package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractEventListener<T> implements MessageListener {
    private final ObjectMapper objectMapper;

    protected T getEvent(Message message, Class<T> event) {
        try {
            log.info("Accepted JSON");
            return objectMapper.readValue(message.getBody(), event);
        } catch (IOException e) {
            log.error("JSON not created");
            throw new RuntimeException(e);
        }
    }
}