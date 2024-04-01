package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.event.PostEvent;
import faang.school.achievement.service.AchievementService;
import org.springframework.stereotype.Component;

@Component
public class OpinionLeaderAchievementHandler extends AbstractAchievementHandler implements EventHandler<PostEvent> {
    private final String achievementTitle = "WRITER";
    private final int requiredEvents = 100;

    public OpinionLeaderAchievementHandler(AchievementCache achievementCache, AchievementService achievementService) {
        super(achievementCache, achievementService);
    }

    @Override
    public void handle(PostEvent event) {
        handle(achievementTitle, event.getAuthorId(), requiredEvents);
    }

}
