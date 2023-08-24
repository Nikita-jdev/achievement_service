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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
class AchievementServiceTest {
    @Mock
    private UserAchievementRepository userAchievementRepository;

    @Mock
    private AchievementProgressRepository achievementProgressRepository;

    @InjectMocks
    private AchievementService achievementService;

    @Test
    public void testHasAchievement() {
        long userId = 1L;
        long achievementId = 2L;

        when(userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId))
                .thenReturn(true);

        boolean hasAchievement = achievementService.hasAchievement(userId, achievementId);

        assertTrue(hasAchievement);
    }

    @Test
    public void testGiveAchievement() {
        long userId = 1L;
        Achievement achievement = new Achievement(); // Создайте нужный Achievement

        achievementService.giveAchievement(userId, achievement);

        verify(userAchievementRepository).save(any(UserAchievement.class));
    }

    @Test
    public void testGetAchievementProgressExisting() {
        long userId = 1L;
        long achievementId = 2L;
        AchievementProgress progress = new AchievementProgress(); // Создайте нужный AchievementProgress

        when(achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId))
                .thenReturn(Optional.of(progress));

        AchievementProgress result = achievementService.getAchievementProgress(userId, achievementId);

        assertNotNull(result);
        assertEquals(progress, result);
    }

    @Test
    public void testGetAchievementProgressNonExisting() {
        long userId = 1L;
        long achievementId = 2L;

        when(achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId))
                .thenReturn(Optional.empty());

        AchievementProgress result = achievementService.getAchievementProgress(userId, achievementId);

        assertNull(result);
    }
}