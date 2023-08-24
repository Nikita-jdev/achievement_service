package faang.school.achievement.service;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AchievementService {
    private final UserAchievementRepository userAchievementRepository;
    private final AchievementProgressRepository achievementProgressRepository;

    @Transactional(readOnly = true)
    public boolean hasAchievement(long userId, long achievementId) {
        return userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId);
    }

    @Transactional
    public void incrementProgress(AchievementProgress achievementProgress) {
        achievementProgress.increment();
        achievementProgressRepository.save(achievementProgress);
    }

    @Transactional
    public void giveAchievement(long userId, Achievement achievement) {
        userAchievementRepository.save(UserAchievement.builder().achievement(achievement).userId(userId).build());
    }

    @Transactional
    public AchievementProgress getAchievementProgress(long userId, long achievementId) {
        return achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId)
                .orElseGet(() -> achievementProgressRepository.createProgressIfNecessary(userId, achievementId));
    }
}