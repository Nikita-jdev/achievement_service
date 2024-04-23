package faang.school.achievement.service;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static faang.school.achievement.model.Rarity.COMMON;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AchievementServiceTest {
    @Mock
    private UserAchievementRepository userAchievementRepository;
    @Mock
    private AchievementProgressRepository achievementProgressRepository;
    @InjectMocks
    private AchievementService achievementService;

    @Test
    void hasAchievementTestTrue() {
        long userId = 1;
        long achievementId = 2;

        when(userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId)).thenReturn(true);
        assertTrue(achievementService.hasAchievement(userId, achievementId));
    }

    @Test
    void hasAchievementTestFalse() {
        long userId = 1;
        long achievementId = 2;

        when(userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId)).thenReturn(false);
        assertFalse(achievementService.hasAchievement(userId, achievementId));
    }

    @Test
    void createProgressIfNecessaryTest() {
        long userId = 1;
        long achievementId = 2;

        assertDoesNotThrow(() -> achievementService.createProgressIfNecessary(userId, achievementId));
    }

    @Test
    void getProgressTest() {
        long userId = 1;
        long achievementId = 2;
        AchievementProgress achievementProgress = getAchievementProgress();
        when(achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId)).thenReturn(Optional.of(achievementProgress));

        assertDoesNotThrow(() -> achievementService.getProgress(userId, achievementId));
    }

    @Test
    void getProgressTestNotFoundEntity() {
        long userId = 1;
        long achievementId = 2;

        assertThrows(EntityNotFoundException.class, () -> achievementService.getProgress(userId, achievementId));
    }

    @Test
    void saveProgress(){
        AchievementProgress achievementProgress = getAchievementProgress();

        achievementService.saveProgress(achievementProgress);
        verify(achievementProgressRepository).save(achievementProgress);
    }
    @Test
    void giveAchievementTest(){
        Achievement achievement = getAchievement();
        long userId = 1;
        UserAchievement userAchievement = UserAchievement.builder()
                .userId(userId)
                .achievement(achievement).build();
        achievementService.giveAchievement(userId,achievement);
        verify(userAchievementRepository).save(userAchievement);
    }

    private AchievementProgress getAchievementProgress() {
        return AchievementProgress.builder()
                .userId(1)
                .currentPoints(2)
                .achievement(getAchievement())
                .build();
    }

    private Achievement getAchievement() {
        return Achievement.builder()
                .id(2)
                .title("Titile")
                .points(50)
                .rarity(COMMON)
                .description("description")
                .build();
    }

}
