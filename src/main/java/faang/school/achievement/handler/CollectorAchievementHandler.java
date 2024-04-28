package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.event.GoalSetEvent;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class CollectorAchievementHandler implements EventHandler<GoalSetEvent> {
    private final AchievementCache achievementCache;
    private final AchievementService achievementService;
    @Setter
    @Value("${achievement.collector}")
    private String achievementTitle;

    @Override
    @Async("executorService")
    @Transactional
    public void handle(GoalSetEvent goalSetEvent) {
        Achievement achievement = achievementCache.get(achievementTitle)
                .orElseThrow(() -> new EntityNotFoundException("Achievement not found"));
        long userId = goalSetEvent.getUserId();
        long achievementId = achievement.getId();
        if (!achievementService.hasAchievement(userId, achievementId)) {
            achievementService.createProgressIfNecessary(userId, achievementId);
            userProgressionInAchievement(userId, achievementId, achievement);
        } else {
            userProgressionInAchievement(userId, achievementId, achievement);
        }
    }

    private void userProgressionInAchievement(Long userId, Long achievementId, Achievement achievement) {
        AchievementProgress achievementProgressByUser = achievementService.getProgress(userId, achievementId);
        achievementProgressByUser.increment();
        achievementService.saveProgress(achievementProgressByUser);
        if (achievementProgressByUser.getCurrentPoints() == achievement.getPoints()) {
            achievementService.giveAchievement(userId, achievement);
            log.info("The user: {} received an achievement {} ", userId, achievementTitle);
        }
    }

}
