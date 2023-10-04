package faang.school.achievement.service;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.repository.AchievementProgressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AchievementProgressService {

    private final AchievementProgressRepository achievementProgressRepository;

    @Transactional
    @Retryable(retryFor = DataIntegrityViolationException.class)
    public AchievementProgress getProgress(long userId, long achievementId) {
        return achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId)
                .orElseGet(() -> createProgress(fillAchievementProgress(userId, achievementId)));
    }

    @Transactional
    public AchievementProgress createProgress(AchievementProgress achievementProgress) {
        return achievementProgressRepository.save(achievementProgress);
    }

    @Transactional
    public AchievementProgress updateProgress(AchievementProgress achievementProgress) {
        return achievementProgressRepository.save(achievementProgress);
    }

    private AchievementProgress fillAchievementProgress(long userId, long achievementId) {
        return AchievementProgress.builder()
                .achievement(Achievement.builder().id(achievementId).build())
                .userId(userId)
                .currentPoints(0)
                .build();
    }
}