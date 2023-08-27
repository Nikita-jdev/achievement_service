package faang.school.achievement.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.GoalSetEventDto;
import faang.school.achievement.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class AbstractEventListener<T> {
    private final List<EventHandler<T>> handlers;
    private final ObjectMapper objectMapper;

    public String getMessage(Message message) {
        String channel = new String(message.getChannel());
        String messageBody = new String(message.getBody());
        log.info("Received message from channel '{}': {}", channel, messageBody);
        return messageBody;
    }

    public void handleEvent(String messageBody, Class<T> type) {
        handlers.forEach(handler -> {
            try {
                handler.handleAsync(objectMapper.readValue(messageBody, type));
            } catch (JsonProcessingException e) {
                log.error("Error processing JSON for {}", GoalSetEventDto.class.getName(), e);
            }
        });
    }
}
