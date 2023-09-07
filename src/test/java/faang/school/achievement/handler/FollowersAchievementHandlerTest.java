package faang.school.achievement.handler;

import faang.school.achievement.dto.FollowerEventDto;
import faang.school.achievement.service.AchievementService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadPoolExecutor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FollowersAchievementHandlerTest {
    @Mock
    private AchievementService achievementService;
    @Mock
    private ThreadPoolExecutor testThreadPoolExecutor;

    private FollowersAchievementHandler followersAchievementHandler;

    private LocalDateTime currentTime;

    private FollowerEventDto followerEventDto;

    private final String ACHIEVEMENT_NAME = "subscribers";

    @BeforeEach
    void setUp() {
        followersAchievementHandler = new FollowersAchievementHandler(achievementService, testThreadPoolExecutor);
        followersAchievementHandler.setFollowersAchievementName(ACHIEVEMENT_NAME);
        currentTime = LocalDateTime.now();
        followerEventDto = FollowerEventDto.builder()
                .followerId(2)
                .followeeId(1)
                .subscriptionTime(currentTime)
                .build();
    }

    @Test
    void handleTest() {
        followersAchievementHandler.handle(followerEventDto);

        verify(testThreadPoolExecutor).execute(any(Runnable.class));
    }
}