package faang.school.achievement.service;

import faang.school.achievement.exception.EntityNotFoundException;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.repository.AchievementProgressRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AchievementProgressServiceTest {

@Mock
private AchievementProgressRepository achievementProgressRepository;

@InjectMocks
private AchievementProgressService achievementProgressService;

private final long userId = 1L;
private final long achievementId = 1L;
private final AchievementProgress progress = new AchievementProgress();

    @Test
    void findByUserIdAndAchievementId_NotFoundTest() {
        when(achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId)).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> achievementProgressService.findByUserIdAndAchievementId(userId, achievementId));
    }

    @Test
    void findByUserIdAndAchievementId_ExistsTest() {
        when(achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId)).thenReturn(Optional.of(progress));
        AchievementProgress result = achievementProgressService.findByUserIdAndAchievementId(userId, achievementId);
        assertEquals(progress, result);
    }

    @Test
    void createProgressIfNecessary() {
        achievementProgressService.createProgressIfNecessary(userId, achievementId);
        Mockito.verify(achievementProgressRepository, Mockito.times(1)).createProgressIfNecessary(userId, achievementId);
    }

    @Test
    void existsByUserIdAndAchievementId_FoundTest() {
        when(achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId)).thenReturn(Optional.of(progress));
        boolean answer = achievementProgressService.existsByUserIdAndAchievementId(userId, achievementId);
        Mockito.verify(achievementProgressRepository, Mockito.times(1)).findByUserIdAndAchievementId(userId, achievementId);
        assertTrue(answer);
    }

    @Test
    void existsByUserIdAndAchievementId_NotFoundTest() {
        when(achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId)).thenReturn(Optional.empty());
        boolean answer = achievementProgressService.existsByUserIdAndAchievementId(userId, achievementId);
        Mockito.verify(achievementProgressRepository, Mockito.times(1)).findByUserIdAndAchievementId(userId, achievementId);
        assertFalse(answer);
    }

    @Test
    void shouldIncrementPoints() {
        progress.setCurrentPoints(0);
        when(achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId)).thenReturn(Optional.of(progress));
        when(achievementProgressRepository.save(progress)).thenReturn(progress);

        AchievementProgress result = achievementProgressService.incrementPoints(userId, achievementId);

        assertEquals(progress, result);
        Mockito.verify(achievementProgressRepository, Mockito.times(1)).save(progress);
    }
}