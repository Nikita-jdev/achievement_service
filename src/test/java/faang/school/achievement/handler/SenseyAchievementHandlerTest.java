package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementProgressService;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.UserAchievementService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;


@ExtendWith(MockitoExtension.class)
class SenseyAchievementHandlerTest {
    @Mock
    private AchievementService achievementService;
    @Mock
    private UserAchievementService userAchievementService;
    @Mock
    private AchievementProgressService achievementProgressService;
    @Mock
    private AchievementCache achievementCache;

    @InjectMocks
    private SenseyAchievementHandler handler;


    @Test
    void achievementAlreadyExistTest() {
        ReflectionTestUtils.setField(handler, "achievementTitle", "Achievement-Sensei");
        long userId = 1;
        long achievementId = 1;
        Achievement achievement = new Achievement();
            achievement.setPoints(100);
            achievement.setId(achievementId);
        AchievementProgress progress = new AchievementProgress();
            progress.setCurrentPoints(100);

        Mockito.when(achievementCache.get("Achievement-Sensei")).thenReturn(achievement);
        Mockito.when(userAchievementService.existsByUserIdAndAchievementId(userId, achievementId)).thenReturn(true);

        handler.handle(userId);

        Mockito.verify(achievementProgressService, Mockito.times(0)).incrementPoints(userId, achievementId);
        Mockito.verify(userAchievementService, Mockito.times(0)).addAchievementToUser(userId, achievementId);
    }
}