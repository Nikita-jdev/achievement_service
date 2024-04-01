package faang.school.achievement.handler;
import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractEventHandler {
    private final AchievementCache achievementCache;
    private final AchievementService achievementService;

    public void abstractHandle(Long userId, String achievementTitle) {
        Achievement achievement = achievementCache.get(achievementTitle);
        if (!achievementService.hasAchievement(userId, achievement.getId())) {
            achievementService.createProgressIfNecessary(userId, achievement.getId());

            AchievementProgress achievementProgress = achievementService.getProgress(userId, achievement.getId());
            achievementService.incrementPoints(achievementProgress);

            if (achievementProgress.getCurrentPoints() >= achievement.getPoints()) {
                achievementService.giveAchievement(userId, achievement);
                log.info("achievementService give achievement "+achievement.getTitle()+" to "+userId);
            }
        }
    }
}
