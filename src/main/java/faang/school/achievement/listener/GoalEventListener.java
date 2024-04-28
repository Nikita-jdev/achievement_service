package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.GoalSetEvent;
import faang.school.achievement.handler.CollectorAchievementHandler;
import org.springframework.stereotype.Component;

@Component
public class GoalEventListener extends AbstractEventListener<GoalSetEvent> {
    private final CollectorAchievementHandler collectorAchievementHandler;
    public GoalEventListener(ObjectMapper objectMapper, Class<GoalSetEvent> type, CollectorAchievementHandler collectorAchievementHandler) {
        super(objectMapper, type);
        this.collectorAchievementHandler = collectorAchievementHandler;
    }

    @Override
    public void workingEvent(GoalSetEvent event) {
        collectorAchievementHandler.handle(event);
    }
}