package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.service.AchievementProgressService;
import faang.school.achievement.service.UserAchievementService;
import org.springframework.stereotype.Component;

@Component
public class SenseyAchievementHandler extends AbstractEventHandler {

    protected SenseyAchievementHandler(AchievementCache achievementCache,
                                       UserAchievementService userAchievementService,
                                       AchievementProgressService achievementProgressService) {
        super("Achievement-Sensei", achievementCache, userAchievementService, achievementProgressService);
    }
}
