package faang.school.achievement.handler;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.service.AchievementProgressService;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.UserAchievementService;
import jakarta.persistence.OptimisticLockException;
import lombok.AllArgsConstructor;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;

@AllArgsConstructor
public abstract class AbstractHandler<T> implements EventHandler<T> {

    private AchievementService achievementService;
    private UserAchievementService userAchievementService;
    private AchievementProgressService achievementProgressService;
    private String title;

    @Async
    @Override
    @Retryable(retryFor = OptimisticLockException.class)
    public void handle(Long userId) {
        Achievement achievement = achievementService.getAchievement(title);

        boolean existsAchievement = userAchievementService.hasAchievement(userId, achievement.getId());

        if (!existsAchievement) {
            AchievementProgress achievementProgress = achievementProgressService.getProgress(userId, achievement.getId());
            achievementProgress.setUpdatedAt(null);
            achievementProgress.increment();
            achievementProgress = achievementProgressService.updateProgress(achievementProgress);

            giveAchievement(userId, achievement, achievementProgress);
        }
    }

    private void giveAchievement(Long userId, Achievement achievement, AchievementProgress progress) {
        if (progress.getCurrentPoints() == achievement.getPoints()) {

            UserAchievement userAchievement = UserAchievement.builder()
                    .achievement(achievement)
                    .userId(userId)
                    .build();

            userAchievementService.giveAchievement(userAchievement);
        }
    }
}
