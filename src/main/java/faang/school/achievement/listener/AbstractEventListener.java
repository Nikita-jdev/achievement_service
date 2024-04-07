package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractEventListener<T> implements MessageListener {

    protected final ObjectMapper objectMapper;
    private final List<EventHandler<T>> handlers;

    public void handle(long userId) {
        handlers.forEach(eventHandler -> eventHandler.handle(userId));
    }

    protected T deserialize(Message message, Class<T> classType) {
        try {
            return objectMapper.readValue(message.getBody(), classType);
        } catch (
                IOException e) {
            log.error("Unsuccessful convert Json to Data");
            throw new RuntimeException(e.getMessage());
        }
    }

}
