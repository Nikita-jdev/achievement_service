package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.GoalSetEvent;
import faang.school.achievement.handler.CollectorAchievementHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class GoalEventListener implements MessageListener {
    private final CollectorAchievementHandler collectorAchievementHandler;
    private final ObjectMapper objectMapper;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            GoalSetEvent goalSetEvent = objectMapper.readValue(message.getBody(), GoalSetEvent.class);
            collectorAchievementHandler.handle(goalSetEvent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}