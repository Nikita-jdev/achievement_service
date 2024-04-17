package faang.school.achievement.service;

import faang.school.achievement.exception.EntityNotFoundException;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.Rarity;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import faang.school.achievement.service.achievement.AchievementService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AchievementServiceTest {
    @InjectMocks
    private AchievementService achievementService;

    @Mock
    private UserAchievementRepository userAchievementRepository;

    @Mock
    private AchievementProgressRepository achievementProgressRepository;

    @Test
    void test_createProgressIfNecessarySuccessful(){
        long userId = 1;
        long achievementId = 8;

        Assertions.assertDoesNotThrow(() -> achievementService.createProgressIfNecessary(userId, achievementId));

        verify(achievementProgressRepository).createProgressIfNecessary(userId, achievementId);
    }

    @Test
    void test_getProgressThrowsEntityNotFound(){
        long userId = 1;
        long achievementId = 200;

        when(achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> achievementService.getProgress(userId, achievementId));
    }

    @Test
    void test_getProgressSuccessful(){
        long userId = 1;
        long achievementId = 8;
        AchievementProgress achievementProgress = AchievementProgress.builder()
                .achievement(getAchievement())
                .userId(userId)
                .currentPoints(1)
                .build();

        when(achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId))
                .thenReturn(Optional.of(achievementProgress));

        Assertions.assertDoesNotThrow(() -> achievementService.getProgress(userId, achievementId));

        verify(achievementProgressRepository).findByUserIdAndAchievementId(userId, achievementId);
    }

    @Test
    void test_hasAchievementTrue(){
        long userId = 1;
        long achievementId = 8;
        boolean statusExcepted = true;

        when(userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId)).thenReturn(statusExcepted);


        boolean status = achievementService.hasAchievement(userId, achievementId);

        Assertions.assertEquals(statusExcepted, status);
        verify(userAchievementRepository).existsByUserIdAndAchievementId(userId, achievementId);
    }

    @Test
    void test_hasAchievementFalse(){
        long userId = 1;
        long achievementId = 8;
        boolean statusExcepted = false;

        when(userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId)).thenReturn(statusExcepted);


        boolean status = achievementService.hasAchievement(userId, achievementId);

        Assertions.assertEquals(statusExcepted, status);
        verify(userAchievementRepository).existsByUserIdAndAchievementId(userId, achievementId);
    }

    @Test
    void test_giveAchievement(){
        long userId = 1;
        Achievement achievement = getAchievement();
        UserAchievement userAchievement = UserAchievement.builder()
                .userId(userId)
                .achievement(achievement)
                .build();

        when(userAchievementRepository.save(userAchievement)).thenReturn(userAchievement);

        Assertions.assertDoesNotThrow(() -> achievementService.giveAchievement(userId, achievement));

        verify(userAchievementRepository).save(userAchievement);
    }

    private Achievement getAchievement(){
        return Achievement.builder()
                .title("Title")
                .description("Description")
                .rarity(Rarity.COMMON)
                .points(2)
                .build();
    }
}
