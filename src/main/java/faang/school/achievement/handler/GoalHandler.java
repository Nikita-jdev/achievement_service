package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.GoalSetEventDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public abstract class GoalHandler implements EventHandler<GoalSetEventDto>{
    private final AchievementCache achievementCache;
    private final AchievementService achievementService;
    private final AsyncTaskExecutor asyncTaskExecutor;
    protected void handle(GoalSetEventDto goalSetEventDto, String achievementTitle) {
        asyncTaskExecutor.execute(() -> {
            Achievement achievement = achievementCache.get(achievementTitle);
            long userId = goalSetEventDto.getUserId();
            long achievementId = achievement.getId();

            if (achievementService.hasAchievement(userId, achievementId)) {
                log.info("User: {} already has achievement {}", userId, achievement.getTitle());
                return;
            }

            AchievementProgress progress = achievementService.getAchievementProgress(userId, achievementId);
            if (progress.getCurrentPoints() < achievement.getPoints()) {
                achievementService.incrementProgress(progress);
                log.info("Incremented progress Id: {}. CurrentPoints: {}", progress.getId(), progress.getCurrentPoints());
                if (progress.getCurrentPoints() >= achievement.getPoints()) {
                    achievementService.giveAchievement(userId, achievement);
                    log.info("Given achievement {} to user {}", achievement.getTitle(), userId);
                }
            }
        });
    }
}

