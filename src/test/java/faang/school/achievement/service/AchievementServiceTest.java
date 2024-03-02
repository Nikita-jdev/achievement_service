package faang.school.achievement.service;

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

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AchievementServiceTest {
    @Mock
    private UserAchievementRepository userAchievementRepository;
    @Mock
    private AchievementProgressRepository achievementProgressRepository;
    @InjectMocks
    private AchievementService achievementService;

    @Test
    void hasAchievementSuccessful() {
        when(userAchievementRepository.existsByUserIdAndAchievementId(anyLong(), anyLong()))
                .thenReturn(true);
        achievementService.hasAchievement(anyLong(), anyLong());
        verify(userAchievementRepository).existsByUserIdAndAchievementId(anyLong(), anyLong());
    }

    @Test
    void createProgressIfNecessarySuccessful() {
        long userId = 1L;
        long achievementId = 1L;
        achievementService.createProgressIfNecessary(userId, achievementId);
        verify(achievementProgressRepository).createProgressIfNecessary(userId, achievementId);
    }

    @Test
    void getProgressSuccessful() {
        when(achievementProgressRepository.findByUserIdAndAchievementId(anyLong(), anyLong()))
                .thenReturn(Optional.of(new AchievementProgress()));
        achievementService.getProgress(anyLong(), anyLong());
        verify(achievementProgressRepository).findByUserIdAndAchievementId(anyLong(), anyLong());
    }

    @Test
    void giveAchievementSuccessful() {
        UserAchievement userAchievement = UserAchievement.builder()
                .userId(1L)
                .achievement(new Achievement())
                .build();
        when(userAchievementRepository.save(userAchievement)).thenReturn(userAchievement);
        achievementService.giveAchievement(1L, new Achievement());

    }
}