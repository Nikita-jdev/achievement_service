package faang.school.achievement.service.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.achievement.AchievementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
public abstract class EventHandler<E> {
    protected final AchievementCache achievementCache;
    protected final AchievementService achievementService;
    protected final String achievementTitle;

    @Async("executorService")
    @Transactional
    public void handle(E event, long userId) {
        log.info("Received event: {}", event);
        Achievement achievement = achievementCache.get(achievementTitle)
                .orElse(achievementService.getAchievementByName(achievementTitle));
        long achievementId = achievement.getId();
        if (!achievementService.hasAchievement(userId, achievementId)) {
            achievementService.createProgressIfNecessary(userId, achievementId);
            AchievementProgress achievementProgress = achievementService.getProgress(userId, achievementId);
            achievementProgress.increment();
            if (isProgressPointsEnoughToAcquire(achievement.getPoints(), achievementProgress.getCurrentPoints())) {
                achievementService.giveAchievement(userId, achievement);
                log.info(String.format("User %d reached the achievement \"%s\"", userId, achievementTitle));
            }
        }
    }

    private boolean isProgressPointsEnoughToAcquire(long requiredPoints, long achievementPoints) {
        return achievementPoints >= requiredPoints;
    }
}
