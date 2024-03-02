package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractEventHandler<T> implements EventHandler<T> {
    private final AchievementService achievementService;
    private final AchievementCache achievementCache;
    private final UserAchievementRepository userAchievementRepository;
    private final AchievementProgressRepository achievementProgressRepository;

    @Transactional
    protected void handleAbstract(long userId, String title) {
        Achievement achievement = achievementCache.getAchievement(title);
        if (achievement == null) {
            log.error("Achievement '" + title + "' not found");
            throw new IllegalArgumentException("Achievement '" + title + "' not found");
        }
        long achievementId = achievement.getId();
        if (achievementService.hasAchievement(userId, achievementId)) {
            log.info("Achievement '" + title + "' already received");
            return;
        }
        AchievementProgress achievementProgress = addProgress(userId, achievementId);
        giveAchievement(userId, achievementProgress, achievement);
        log.info("Librarian achievement update completed");
    }


    private AchievementProgress addProgress(long userId, long achievementId) {
        achievementService.createProgressIfNecessary(userId, achievementId);
        log.info("Create progress");
        AchievementProgress progress = achievementService.getProgress(userId, achievementId);
        progress.increment();
        log.info("Progress increased");
        return achievementProgressRepository.save(progress);
    }

    private void giveAchievement(long userId, AchievementProgress progress, Achievement achievement) {
        if (progress.getCurrentPoints() >= achievement.getPoints()) {
            achievementService.giveAchievement(userId, achievement);
            UserAchievement userAchievement = UserAchievement.builder()
                    .userId(userId)
                    .achievement(achievement)
                    .build();
            userAchievementRepository.save(userAchievement);
            log.info("The achievement {} was awarded to the user {}", achievement.getTitle(), userId);
        }
    }
}