package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.handler.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.io.IOException;
import java.util.List;

public abstract class AbstractEventListener<T> implements MessageListener {
    @Autowired
    private ObjectMapper objectMapper;
    private final Class<T> eventType;

    final List<EventHandler<T>> eventHandlers;

    public AbstractEventListener(Class<T> eventType, List<EventHandler<T>> eventHandlers) {
        this.eventType = eventType;
        this.eventHandlers = eventHandlers;
    }

    private void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            objectMapper.findAndRegisterModules();
            T event = objectMapper.readValue(message.getBody(), eventType);
            processEvent(event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract void processEvent(T event);
}
