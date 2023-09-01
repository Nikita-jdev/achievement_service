package faang.school.achievement.handler;

import faang.school.achievement.dto.FollowerEventDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.service.AchievementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FollowersAchievementHandlerTest {
    @Mock
    private AchievementService achievementService;

    private FollowersAchievementHandler followersAchievementHandler;

    private Achievement achievement;

    private LocalDateTime currentTime;

    private FollowerEventDto followerEventDto;

    private final String ACHIEVEMENT_NAME = "subscribers";

    @BeforeEach
    void setUp() {
        followersAchievementHandler = new FollowersAchievementHandler(achievementService);
        followersAchievementHandler.setFollowersAchievementName(ACHIEVEMENT_NAME);
        UserAchievement firstUserAchievement = UserAchievement.builder()
                .id(1)
                .userId(1)
                .build();
        UserAchievement secondUserAchievement = UserAchievement.builder()
                .id(2)
                .userId(2)
                .build();
        achievement = Achievement.builder()
                .id(1)
                .points(100)
                .userAchievements(List.of(firstUserAchievement, secondUserAchievement))
                .build();
        currentTime = LocalDateTime.now();
        followerEventDto = FollowerEventDto.builder()
                .followerId(2)
                .followeeId(1)
                .subscriptionTime(currentTime)
                .build();
    }

    @Test
    void handleTest() {
        when(achievementService.getAchievement(ACHIEVEMENT_NAME))
                .thenReturn(achievement);
        when(achievementService.hasAchievement(1, 1))
                .thenReturn(false);
        when(achievementService.getProgress(1, 1))
                .thenReturn(100L);

        followersAchievementHandler.handle(followerEventDto);

        verify(achievementService).getAchievement(ACHIEVEMENT_NAME);
        verify(achievementService).hasAchievement(1, 1);
        verify(achievementService).checkAndCreateAchievementProgress(1, 1);
        verify(achievementService).getProgress(1, 1);
        verify(achievementService).giveAchievement(1, ACHIEVEMENT_NAME);
    }
}