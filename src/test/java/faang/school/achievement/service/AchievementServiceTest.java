package faang.school.achievement.service;

import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AchievementServiceTest {

    @InjectMocks
    private AchievementService achievementService;
    @Mock
    private UserAchievementRepository userAchievementRepository;
    @Mock
    private AchievementProgressRepository achievementProgressRepository;
    @Mock
    private AchievementProgress achievementProgress;

    @Test
    void testHasAchievementUseRepositoryToConfirmPresence() {
        when(userAchievementRepository.existsByUserIdAndAchievementId(anyLong(), anyLong()))
                .thenReturn(true);

        achievementService.hasAchievement(anyLong(), anyLong());

        verify(userAchievementRepository, times(1))
                .existsByUserIdAndAchievementId(anyLong(), anyLong());
    }

    @Test
    void testHasAchievementUseRepositoryToConfirmAbsence() {
        when(userAchievementRepository.existsByUserIdAndAchievementId(anyLong(), anyLong()))
                .thenReturn(false);

        achievementService.hasAchievement(anyLong(), anyLong());

        verify(userAchievementRepository, times(1))
                .existsByUserIdAndAchievementId(anyLong(), anyLong());
    }

    @Test
    void testQueriedForMissingProgress_thenRespondsWithEmptyOptional() {
        when(achievementProgressRepository.findByUserIdAndAchievementId(anyLong(), anyLong()))
                .thenReturn(Optional.empty());

        achievementService.getProgress(anyLong(), anyLong());

        verify(achievementProgressRepository, times(1))
                .findByUserIdAndAchievementId(anyLong(), anyLong());
    }

    @Test
    void testCreateInvokesRepositoryMethodOnce() {
        long userId = 1L;
        long achievementId = 1L;

        achievementService.createProgressIfNecessary(userId, achievementId);

        verify(achievementProgressRepository, times(1))
                .createProgressIfNecessary(userId, achievementId);
    }

    @Test
    void testWhenCalledInvokesIncrementAndSavesProgress() {
        when(achievementProgressRepository.save(any(AchievementProgress.class)))
                .thenReturn(achievementProgress);

        achievementService.incrementProgress(achievementProgress);

        verify(achievementProgress, times(1)).increment();
        verify(achievementProgressRepository, times(1)).save(achievementProgress);
    }
}