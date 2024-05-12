package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.SkillAcquiredEvent;
import faang.school.achievement.handler.SkillFullAchievementHandler;
import org.springframework.stereotype.Component;

@Component
public class SkillEventListener extends AbstractEventListener<SkillAcquiredEvent> {
    public SkillEventListener(ObjectMapper objectMapper, SkillFullAchievementHandler skillFullAchievementHandler) {
        super(objectMapper, skillFullAchievementHandler, SkillAcquiredEvent.class);
    }

    @Override
    public void process(SkillAcquiredEvent event) {
        skillFullAchievementHandler.handle(event);
    }
}
