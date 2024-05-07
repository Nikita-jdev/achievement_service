package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.GoalSetEvent;
import faang.school.achievement.handler.CollectorAchievementHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GoalEventListener extends AbstractEventListener<GoalSetEvent> {
    @Autowired
    private final CollectorAchievementHandler collectorAchievementHandler;

    public GoalEventListener(ObjectMapper objectMapper, CollectorAchievementHandler collectorAchievementHandler) {
        super(objectMapper);
        this.collectorAchievementHandler = collectorAchievementHandler;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        GoalSetEvent event = convert(message.getBody(), GoalSetEvent.class);
        collectorAchievementHandler.handle(event);
        log.info("event processed successfully {}", event);
    }
}