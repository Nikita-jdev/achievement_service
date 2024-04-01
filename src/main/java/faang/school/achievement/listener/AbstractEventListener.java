package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractEventListener<T> {

    private final List<EventHandler<T>> eventHandlers;
    private final ObjectMapper mapper;

    protected void handle(Message message, Class<T> type) {
        T event = mapToEvent(message, type);
        eventHandlers.forEach(eventHandler -> eventHandler.handle(event));
    }

    private T mapToEvent(Message message, Class<T> type) {
        try {
            return mapper.readValue(message.getBody(), type);
        } catch (IOException e) {
            log.error("Error deserializing JSON to object", e);
            throw new RuntimeException(e);
        }
    }

}
