package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.TeamEventDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ManagerAchievementHandler implements EventHandler<TeamEventDto> {

    private final AchievementCache achievementCache;
    private final AchievementService achievementService;

    @Value("${achievement-service.achievement_manager}")
    private String achievementId;

    @Override
    public void handle(Long userId) {
        Achievement achievement = achievementCache.get(achievementId);
        if (achievement != null && !achievementService.hasAchievement(userId, achievement.getId())) {
            AchievementProgress achievementProgress = achievementService
                    .createProgressIfNecessaryOrGet(userId, achievement.getId());
            AchievementProgress userAchievementProgress = achievementService.incrementProgress(achievementProgress);

            if (userAchievementProgress.getCurrentPoints() >= achievement.getPoints()) {
                achievementService.giveAchievement(userId, achievement);
                log.info("Получили  achievement progress {} для userId: {}",
                        userAchievementProgress.getAchievement().getId(), userId);
            }
        }
    }
}