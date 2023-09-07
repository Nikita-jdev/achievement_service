package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.EventPostDto;
import faang.school.achievement.handler.AbstractAchievementHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostEventListener implements MessageListener {

    private final ObjectMapper objectMapper;
    private final List<AbstractAchievementHandler<EventPostDto>> handlers;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.info("PostEventListener has received a new message from Redis");
        try {
            EventPostDto eventPostDto = objectMapper.readValue(message.getBody(), EventPostDto.class);
            handlers.forEach(handler -> handler.handle(eventPostDto));
        } catch (IOException e) {
            log.error("IOException while parsing message in PostEventListener...");
            throw new RuntimeException(e);
        }
    }
}
