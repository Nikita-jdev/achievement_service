package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public abstract class AbstractEventListener<T> implements MessageListener {
    private final ObjectMapper objectMapper;
    private final List<EventHandler<T>> handlers;

    protected T getEvent(Message message, Class<T> eventType) {
        try {
            return objectMapper.readValue(message.getBody(), eventType);
        } catch (IOException e) {
            log.error("Error processing incoming event", e);
            throw new RuntimeException(e);
        }
    }

    protected void runHandlers(long userId) {
        handlers.forEach(handler -> handler.handle(userId));
    }
}