package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementProgressService;
import faang.school.achievement.service.UserAchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;


public abstract class AbstractEventHandler implements EventHandler {
    private final String achievementTitle;
    private final AchievementCache achievementCache;
    private final UserAchievementService userAchievementService;
    private final AchievementProgressService achievementProgressService;

    @Autowired
    protected AbstractEventHandler(String achievementTitle, AchievementCache achievementCache, UserAchievementService userAchievementService, AchievementProgressService achievementProgressService) {
        this.achievementTitle = achievementTitle;
        this.achievementCache = achievementCache;
        this.userAchievementService = userAchievementService;
        this.achievementProgressService = achievementProgressService;
    }

    @Override
    @Async
    public void handle(Long userId) {
        Achievement achievement = achievementCache.get(achievementTitle);

        if (!userAchievementService.existsByUserIdAndAchievementId(userId, achievement.getId())) {
            AchievementProgress progress = achievementProgressService.incrementPoints(userId, achievement.getId());
            if (progress.getCurrentPoints() >= achievement.getPoints()) {
                userAchievementService.addAchievementToUser(userId, achievement.getId());
            }
        }
    }
}
