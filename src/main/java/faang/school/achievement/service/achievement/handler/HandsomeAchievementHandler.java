package faang.school.achievement.service.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.EventProfilePic;
import faang.school.achievement.exception.EntityNotFoundException;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.achievement.AchievementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class HandsomeAchievementHandler implements EventHandler<EventProfilePic> {
    private final AchievementCache achievementCache;
    private final AchievementService achievementService;
    @Value("${achievement.profile_pic.handsome}")
    private String profileAchievementName;

    @Override
    @Async("executorService")
    @Transactional
    public void handle(EventProfilePic event) {
        log.info("Received comment event: {}", event);
        Achievement achievement = achievementCache.get(profileAchievementName)
                .orElseThrow(() -> new EntityNotFoundException("Achievement does not exist"));
        long userId = event.getUserId();
        long achievementId = achievement.getId();
        if (!achievementService.hasAchievement(userId, achievementId)) {
            achievementService.createProgressIfNecessary(userId, achievementId);
            AchievementProgress achievementProgress = achievementService.getProgress(userId, achievementId);
            achievementProgress.increment();
            if (isProgressPointsEnoughToAcquire(achievement.getPoints(), achievementProgress.getCurrentPoints())) {
                achievementService.giveAchievement(event.getUserId(), achievement);
                log.info(String.format("User %d reached the achievement \"%s\"", event.getUserId(), profileAchievementName));
            }
        }
    }

    private boolean isProgressPointsEnoughToAcquire(long requiredPoints, long achievementPoints) {
        return achievementPoints >= requiredPoints;
    }
}
