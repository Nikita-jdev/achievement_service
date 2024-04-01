package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public abstract class AbstractAchievementHandler {

    private final AchievementCache achievementCache;
    private final AchievementService achievementService;

    @Transactional
    protected void handle(String achievementTitle, long userId, int requiredEvents) {
        Achievement achievement = achievementCache.get(achievementTitle);
        long achievementId = achievement.getId();

        if (!achievementService.hasAchievement(userId, achievementId)) {
            achievementService.createProgressIfNecessary(userId, achievementId);
            AchievementProgress progress = achievementService.getProgress(userId, achievementId);
            achievementService.incrementPoints(progress);
            if (progress.getCurrentPoints() >= requiredEvents) {
                achievementService.giveAchievement(userId, achievement);
            }
        }
    }

}
