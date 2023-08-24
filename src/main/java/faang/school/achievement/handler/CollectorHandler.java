package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.GoalSetEventDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CollectorHandler extends GoalHandler {
    private final AchievementCache achievementCache;
    private final AchievementService achievementService;
    private final AsyncTaskExecutor asyncTaskExecutor;

    @Override
    public void handle(GoalSetEventDto goalSetEventDto) {
        asyncTaskExecutor.execute(() -> {
            Achievement collector = achievementCache.get("Collector");
            long userId = goalSetEventDto.getUserId();
            long achievementId = collector.getId();

            if (achievementService.hasAchievement(userId, achievementId)) {
                return;
            }

            AchievementProgress progress = achievementService.getAchievementProgress(userId, achievementId);
            if (progress.getCurrentPoints() < collector.getPoints()) {
                progress.increment();
                if (progress.getCurrentPoints() >= collector.getPoints()) {
                    achievementService.giveAchievement(userId, collector);
                }
            }
        });
    }

    @Override
    public void handleAsync(GoalSetEventDto goalSetEventDto) {
        handle(goalSetEventDto);
    }
}
