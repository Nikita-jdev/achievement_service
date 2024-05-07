package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.SkillAcquiredEvent;
import faang.school.achievement.handler.SkillFullAchievementHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
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
