package faang.school.achievement.service;

import faang.school.achievement.event.SkillAcquiredEvent;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AchievementServiceTest {
    @Mock
    private UserAchievementRepository userAchievementRepository;
    @Mock
    private AchievementProgressRepository achievementProgressRepository;
    @InjectMocks
    private AchievementService achievementService;
    private Achievement achievement;
    private SkillAcquiredEvent event;


    @BeforeEach
    public void setup() {
        achievement = new Achievement();
        achievement.setId(1L);

        event = new SkillAcquiredEvent();
        event.setAuthorId(1L);
        event.setReceiverId(2L);
        event.setSkillId(3L);
    }

    @Test
    public void testHasAchievementSuccess() {
        Mockito.when(userAchievementRepository.existsByUserIdAndAchievementId(event.getReceiverId(), achievement.getId())).thenReturn(true);
        boolean result = achievementService.hasAchievement(2L, 1L);
        Mockito.verify(userAchievementRepository, Mockito.times(1)).existsByUserIdAndAchievementId(2L, 1L);
        Assertions.assertTrue(result);
    }

    @Test
    public void testHasAchievementFail() {
        Mockito.when(userAchievementRepository.existsByUserIdAndAchievementId(event.getReceiverId(), achievement.getId())).thenReturn(false);
        boolean result = achievementService.hasAchievement(2L, 1L);
        Mockito.verify(userAchievementRepository, Mockito.times(1)).existsByUserIdAndAchievementId(2L, 1L);
        Assertions.assertFalse(result);
    }

    @Test
    public void getProgressSuccess() {
        AchievementProgress achievementProgress = new AchievementProgress();
        achievementProgress.setId(1L);
        achievementProgress.setAchievement(achievement);
        achievementProgress.setUserId(2L);
        achievementProgress.setCurrentPoints(5);

        Mockito.when(achievementProgressRepository.findByUserIdAndAchievementId(event.getReceiverId(), achievement.getId())).thenReturn(Optional.of(achievementProgress));

        AchievementProgress result = achievementService.getProgress(2L, 1L);

        Mockito.verify(achievementProgressRepository, Mockito.times(1)).findByUserIdAndAchievementId(2L, 1L);

        Assertions.assertEquals(achievementProgress, result);
    }

    @Test
    public void testSaveUserAchievementSuccess() {
        UserAchievement userAchievement = new UserAchievement();
        userAchievement.setUserId(2L);
        userAchievement.setAchievement(achievement);

        Mockito.when(userAchievementRepository.save(userAchievement)).thenReturn(userAchievement);
        UserAchievement save = userAchievementRepository.save(userAchievement);
        Mockito.verify(userAchievementRepository, Mockito.times(1)).save(userAchievement);
        Assertions.assertEquals(save, userAchievement);
    }
}
