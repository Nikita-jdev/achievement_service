package faang.school.achievement.service.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.EventProfilePic;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.achievement.AchievementService;
import faang.school.achievement.service.achievement.handler.HandsomeAchievementHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HandsomeAchievementHandlerTest {
    @InjectMocks
    private HandsomeAchievementHandler handsomeAchievementHandler;
    @Mock
    private AchievementCache achievementCache;
    @Mock
    private AchievementService achievementService;

    private AchievementProgress achievementProgress;

    private int progressPoints;

    private int expectedPoints;

    @BeforeEach
    void setUp() throws Exception {
        ReflectionTestUtils.setField(handsomeAchievementHandler, "achievementTitle", "HANDSOME");

        progressPoints = 1;
        expectedPoints = progressPoints + 1;

        achievementProgress = AchievementProgress.builder()
                .id(1L)
                .achievement(new Achievement())
                .userId(1L)
                .currentPoints(progressPoints)
                .build();
    }

    @Test
    void handle_ValidArgs() {
        when(achievementCache.get(anyString())).thenReturn(Optional.of(Achievement.builder().points(1).build()));
        when(achievementService.hasAchievement(anyLong(), anyLong())).thenReturn(false);
        when(achievementService.getProgress(anyLong(), anyLong())).thenReturn(achievementProgress);

        handsomeAchievementHandler.handle(new EventProfilePic(), 1);

        assertEquals(expectedPoints, achievementProgress.getCurrentPoints());
        verify(achievementCache, times(1)).get(anyString());
        verify(achievementService, times(1)).hasAchievement(anyLong(), anyLong());
        verify(achievementService, times(1)).createProgressIfNecessary(anyLong(), anyLong());
        verify(achievementService, times(1)).getProgress(anyLong(), anyLong());
        verify(achievementService, times(1)).giveAchievement(anyLong(), any(Achievement.class));
    }
}
