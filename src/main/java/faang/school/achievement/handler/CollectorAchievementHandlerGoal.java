package faang.school.achievement.handler;

import faang.school.achievement.dto.GoalSetEventDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CollectorAchievementHandlerGoal extends AbstractGoalEventHandler<GoalSetEventDto> {

    private final String title;

    @Autowired
    public CollectorAchievementHandlerGoal(AchievementService achievementService,
                                           @Value("${achievements.goal.collector.title}") String title) {
        super(achievementService);
        this.title = title;
    }

    @Async
    @Retryable(value = {OptimisticLockingFailureException.class}, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    @Override
    public void handle(GoalSetEventDto event) {
        Achievement achievement = getByTitle(title);

        if (!achievementService.hasAchievement(event.getUserId(), achievement.getId())) {
            AchievementProgress achievementProgress = getAchievementProgress(event.getUserId(), achievement);

            achievementProgress.setUpdatedAt(LocalDateTime.now());
            achievementProgress.increment();

            achievementProgress = achievementService.updateAchievementProgress(achievementProgress);

            giveAchievementIfScoredPoints(achievementProgress,achievement);
        }
    }

}
