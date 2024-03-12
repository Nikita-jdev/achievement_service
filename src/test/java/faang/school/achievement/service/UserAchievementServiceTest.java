package faang.school.achievement.service;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.UserAchievementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserAchievementServiceTest {

    @Mock
    private UserAchievementRepository userAchievementRepository;
    @Mock
    private AchievementService achievementService;

    @InjectMocks
    private UserAchievementService userAchievementService;
    private final long userId = 1L;
    private final long achievementId = 1L;
    private final UserAchievement userAchievement = new UserAchievement();

    @Test
    void existsByUserIdAndAchievementId() {
        Mockito.when(userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId))
                .thenReturn(true);
        assertTrue(userAchievementService.existsByUserIdAndAchievementId(userId, achievementId));
    }

    @Test
    void addAchievementToUser() {
        Achievement achievement = new Achievement();
        Mockito.when(userAchievementRepository.save(Mockito.any()))
                .thenReturn(userAchievement);
        Mockito.when(achievementService.findById(achievementId))
                .thenReturn(achievement);
        assertEquals(userAchievement, userAchievementService.addAchievementToUser(userId, achievementId));
    }
}