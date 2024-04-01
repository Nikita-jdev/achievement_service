package faang.school.achievement.handler.mentorship;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.MentorshipStartEvent;
import faang.school.achievement.handler.AbstractAchievementHandler;
import faang.school.achievement.handler.EventHandler;
import faang.school.achievement.service.AchievementService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class SenseiAchievementHandler extends AbstractAchievementHandler implements EventHandler<MentorshipStartEvent> {

    private final String achievementTitle = "SENSEI";
    private final int requiredEvents = 30;

    public SenseiAchievementHandler(AchievementCache achievementCache, AchievementService achievementService) {
        super(achievementCache, achievementService);
    }

    @Async
    @Override
    public void handle(MentorshipStartEvent event) {
        super.handle(achievementTitle, event.getMentorId(), requiredEvents);
    }

}
