package faang.school.achievement.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

@ExtendWith(MockitoExtension.class)
public class AchievementServiceTest {
    @InjectMocks
    private AchievementService achievementService;
    @Mock
    private AchievementProgressRepository achievementProgressRepository;
    @Mock
    private UserAchievementRepository userAchievementRepository;


    @Test
    void testAchievementProgressNotFound() {
        when(achievementProgressRepository.findByUserIdAndAchievementId(1L, 2L)).thenReturn(Optional.empty());
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                () -> achievementService.getProgress(1L, 2L));
        assertEquals("Achievement progress not found", illegalArgumentException.getMessage());
    }

    @Test
    void testHasAchievementVerify() {
        achievementService.hasAchievement(1L, 2L);
        verify(userAchievementRepository, times(1)).existsByUserIdAndAchievementId(1L, 2L);
    }

    @Test
    void testCreateProgressIfNecessaryVerify() {
        achievementService.createProgressIfNecessary(1L, 2L);
        verify(achievementProgressRepository, times(1)).createProgressIfNecessary(1L, 2L);
    }

    @Test
    void testGetProgressVerify() {
        when(achievementProgressRepository.findByUserIdAndAchievementId(1L, 2L)).thenReturn(Optional.of(new AchievementProgress()));
        achievementService.getProgress(1L, 2L);
        verify(achievementProgressRepository, times(1)).findByUserIdAndAchievementId(1L, 2L);
    }

    @Test
    void testIncrementPointsVerify() {
        AchievementProgress achievementProgress = new AchievementProgress();
        achievementService.incrementPoints(achievementProgress);
        verify(achievementProgressRepository, times(1)).save(achievementProgress);
    }

    @Test
    void testGiveAchievementVerify() {
        Achievement achievement = new Achievement();
        UserAchievement userAchievement = new UserAchievement();
        userAchievement.setAchievement(achievement);
        userAchievement.setUserId(1L);
        achievementService.giveAchievement(1L, achievement);
        verify(userAchievementRepository, times(1)).save(userAchievement);
    }
}
