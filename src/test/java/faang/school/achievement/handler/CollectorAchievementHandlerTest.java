package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.event.GoalSetEvent;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static faang.school.achievement.model.Rarity.COMMON;

@ExtendWith(MockitoExtension.class)
public class CollectorAchievementHandlerTest {
    @Mock
    private AchievementCache achievementCache;
    @Mock
    private AchievementService achievementService;

    @InjectMocks
    private CollectorAchievementHandler collectorAchievementHandler;

    @Test
    void handleTest() {
        collectorAchievementHandler.setAchievementTitle("collector");
        long userId = 1;
        GoalSetEvent goalSetEvent = GoalSetEvent.builder()
                .userId(userId)
                .goalId(2L).build();
        Achievement firstAchievement = Achievement.builder()
                .id(2)
                .title("collector")
                .points(50)
                .rarity(COMMON)
                .description("description")
                .build();


        when(achievementCache.get(anyString())).thenReturn(Optional.of(Achievement.builder()
                .title("collector")
                .id(2).build()));
        when(achievementService.hasAchievement(anyLong(), anyLong())).thenReturn(false);
        when(achievementService.getProgress(anyLong(), anyLong())).thenReturn(AchievementProgress.builder()
                .userId(userId)
                .id(3L)
                .achievement(firstAchievement)
                .currentPoints(0)
                .build());
        Assertions.assertDoesNotThrow(() -> collectorAchievementHandler.handle(goalSetEvent));
    }


}
