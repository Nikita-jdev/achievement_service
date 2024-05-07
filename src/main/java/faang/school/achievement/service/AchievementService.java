package faang.school.achievement.service;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AchievementService {
    private final AchievementProgressRepository achievementProgressRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final AchievementCache achievementCache;

    public void workWithAchievement(long userId, String achievementTitle) {
        Achievement achievement = achievementCache.get(achievementTitle)
                .orElseThrow(() -> new EntityNotFoundException("Achievement not found"));
        long achievementId = achievement.getId();
        if (!hasAchievement(userId, achievementId)) {
            createProgressIfNecessary(userId, achievementId);
            userProgressionInAchievement(userId, achievementId, achievement, achievementTitle);
        } else {
            userProgressionInAchievement(userId, achievementId, achievement, achievementTitle);
        }
    }

    private void userProgressionInAchievement(Long userId, Long achievementId, Achievement achievement, String achievementTitle) {
        AchievementProgress achievementProgressByUser = getProgress(userId, achievementId);
        achievementProgressByUser.increment();
        saveProgress(achievementProgressByUser);
        if (achievementProgressByUser.getCurrentPoints() == achievement.getPoints()) {
            giveAchievement(userId, achievement);
            log.info("The user: {} received an achievement {} ", userId, achievementTitle);
        }
    }

    @Transactional(readOnly = true)
    public boolean hasAchievement(long userId, long achievementId) {
        return userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId);
    }

    @Transactional
    public void createProgressIfNecessary(long userId, long achievementId) {
        achievementProgressRepository.createProgressIfNecessary(userId, achievementId);

    }

    @Transactional(readOnly = true)
    public AchievementProgress getProgress(long userId, long achievementId) {
        return achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("records with the specified parameters:" +
                        " user id: %s, achievementId: %s , not in the database", userId, achievementId)));

    }

    @Transactional
    public void saveProgress(AchievementProgress achievementProgress) {
        achievementProgressRepository.save(achievementProgress);
        log.info("the user: {} has progressed in points to achieve", achievementProgress.getUserId());
    }

    @Transactional
    public void giveAchievement(long userId, Achievement achievement) {
        userAchievementRepository.save(UserAchievement.builder()
                .achievement(achievement)
                .userId(userId)
                .build());
    }
}