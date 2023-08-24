package faang.school.achievement.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.GoalSetEventDto;
import faang.school.achievement.handler.GoalHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class GoalSetListener implements MessageListener {
    private final List<GoalHandler> goalHandlers;
    private final ObjectMapper objectMapper;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(message.getChannel());
        String messageBody = new String(message.getBody());
        log.info("Received message from channel '{}': {}", channel, messageBody);
        goalHandlers.forEach(goalHandler -> {
            try {
                goalHandler.handleAsync(objectMapper.readValue(messageBody, GoalSetEventDto.class));
            } catch (JsonProcessingException e) {
                log.error("Error processing JSON for {}", GoalSetEventDto.class.getName(), e);
            }
        });
    }
}
