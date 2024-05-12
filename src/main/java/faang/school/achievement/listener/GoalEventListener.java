package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.GoalSetEvent;
import faang.school.achievement.handler.CollectorAchievementHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GoalEventListener extends AbstractEventListener<GoalSetEvent> {
    public GoalEventListener(ObjectMapper objectMapper, CollectorAchievementHandler collectorAchievementHandler) {
        super(objectMapper, GoalSetEvent.class);
        this.collectorAchievementHandler = collectorAchievementHandler;
    }

    private final CollectorAchievementHandler collectorAchievementHandler;

    @Override
    public void process(GoalSetEvent event) {
        collectorAchievementHandler.handle(event);
    }
}