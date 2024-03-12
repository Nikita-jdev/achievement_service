package faang.school.achievement.service;

import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.UserAchievementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAchievementService {
    private final UserAchievementRepository userAchievementRepository;
    private final AchievementService achievementService;

    public boolean existsByUserIdAndAchievementId(long userId, long achievementId) {
        return userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId);
    }

    public UserAchievement addAchievementToUser(long userId, long achievementId) {
        UserAchievement achievement = UserAchievement.builder() //не уверен что это правильный подход
                .userId(userId)
                .achievement(achievementService.findById(achievementId))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        return userAchievementRepository.save(achievement);
    }
}
