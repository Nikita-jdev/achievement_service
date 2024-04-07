package faang.school.achievement.service;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AchievementServiceTest {

    @InjectMocks
    private AchievementService achievementService;
    @Mock
    private UserAchievementRepository userAchievementRepository;
    @Mock
    private AchievementProgressRepository achievementProgressRepository;
    @Mock
    private AchievementCache achievementCache;

    @Test
    void hasAchievement() {
        long userId = 1L;
        long achievementId = 2L;
        achievementService.hasAchievement(userId,achievementId);
        verify(userAchievementRepository, times(1))
                .existsByUserIdAndAchievementId(userId, achievementId);
    }

    @Test
    void getAchievement() {
        String title = "CELEBRITY";
        achievementService.getAchievement(title);

        verify(achievementCache, times(1)).getAchievement(title);
    }

    @Test
    void getProgress() {
        long userId = 1L;
        long achievementId = 2L;
        achievementService.getProgress(userId, achievementId);

        verify(achievementProgressRepository, times(1))
                .createProgressIfNecessary(userId, achievementId);
        verify(achievementProgressRepository, times(1))
                .findByUserIdAndAchievementId(userId, achievementId);
    }

    @Test
    void saveAchievementProgress() {
        AchievementProgress progress = AchievementProgress.builder()
                .id(1L)
                .userId(1L)
                .build();
        achievementService.saveAchievementProgress(progress);

        verify(achievementProgressRepository, times(1)).save(progress);
    }

    @Test
    void giveAchievement() {
        long userId = 1L;
        Achievement achievement = Achievement.builder()
                .id(1L)
                .build();
        UserAchievement userAchievement = UserAchievement.builder()
                .userId(userId)
                .achievement(achievement)
                .build();
        achievementService.giveAchievement(userId, achievement);

        verify(userAchievementRepository, times(1)).save(userAchievement);
    }
}