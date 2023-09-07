package faang.school.achievement.handler;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public abstract class AbstractAchievementHandler<T> implements EventHandler<T> {

    private final AchievementService achievementService;

    public void handleAchievement(long userId, String achievementName) {
        Achievement achievement = achievementService.getAchievement(achievementName);
        long achievementId = achievement.getId();

        if (!achievementService.hasAchievement(userId, achievementId)) {
            long currentProgress = achievementService.getProgress(userId, achievementId);

            if (currentProgress >= achievement.getPoints()) {
                achievementService.giveAchievement(achievement, userId);
                log.info("User with ID {} received achievement: {}",userId, achievementName);
            }
        }
    }
}