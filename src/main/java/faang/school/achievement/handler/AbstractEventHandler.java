package faang.school.achievement.handler;

import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractEventHandler<T> implements EventHandler<T> {

    private final AchievementService achievementService;
    private final AchievementMapper achievementMapper;

    @Override
    public void handle(Long userId) {
        Achievement achievement = achievementMapper
                .toEntity(achievementService.getAchievement(getAchievementName()));
        long achievementId = achievement.getId();

        if (!achievementService.hasAchievement(userId, achievementId)) {
            AchievementProgress achievementProgress = achievementService.getProgress(userId, achievementId);

            if (achievementProgress.getCurrentPoints() < getRequiredPoints()) {
                achievementProgress.increment();
                achievementService.saveAchievementProgress(achievementProgress);
            } else {
                achievementService.giveAchievement(userId, achievement);
            }
        }
    }

    protected abstract String getAchievementName();

    protected abstract long getRequiredPoints();

}