package faang.school.achievement.handler.post;

import faang.school.achievement.dto.PostEventDto;
import faang.school.achievement.exception.EntityNotFoundException;
import faang.school.achievement.model.Rarity;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import org.mockito.Mock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class OpinionLeaderAchievementHandlerTest {
    @InjectMocks
    private OpinionLeaderAchievementHandler handler;
    @Mock
    private AchievementCache achievementCache;
    @Mock
    private AchievementService achievementService;
    private Achievement achievement;
    private AchievementProgress progress;
    private PostEventDto dto;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(handler, "achievementTitle", "Achievement-OpinionLeader");

        dto = PostEventDto.builder()
                .authorId(1L)
                .postId(2L)
                .build();

        achievement = Achievement.builder()
                .id(1L)
                .title("Achievement-OpinionLeader")
                .description("Description")
                .rarity(Rarity.EPIC)
                .points(1000)
                .build();

        progress = AchievementProgress.builder()
                .userId(1L)
                .achievement(achievement)
                .currentPoints(1)
                .build();

        when(achievementCache.get("Achievement-OpinionLeader")).thenReturn(achievement);
        when(achievementService.userHasAchievement(1L, 1L)).thenReturn(false);
        when(achievementService.getProgress(1L, 1L)).thenReturn(progress);

    }

    @Test
    void testHandleAchievement_UserAlreadyHasAchievement() {
        when(achievementService.userHasAchievement(1L, 1L)).thenReturn(true);

        handler.handle(dto);

        verify(achievementService, never()).createProgressIfNecessary(1L, 1L);
        verify(achievementService, never()).getProgress(1L, 1L);
        verify(achievementService, never()).incrementProgress(progress);
        verify(achievementService, never()).giveAchievement(1L, achievement);
    }

    @Test
    void getAchievementTitle() {
        assertEquals("Achievement-OpinionLeader", handler.getAchievementTitle());
    }

    @Test
    void handle_shouldInvokeAchievementCacheGetMethod() {
        handler.handle(dto);
        verify(achievementCache).get("Achievement-OpinionLeader");
    }

    @Test
    void testHandleAchievement_EntityNotFoundException() {
        when(achievementService.getProgress(1L, 1L)).thenThrow(new EntityNotFoundException("Test Exception"));

        assertThrows(EntityNotFoundException.class, () -> handler.handle(dto));

        verify(achievementService).userHasAchievement(1L, 1L);
        verify(achievementService).getProgress(1L, 1L);

    }

    @Test
    void testHandleAchievement_EpicRarity() {
        ReflectionTestUtils.setField(achievement, "rarity", Rarity.EPIC);
        when(achievementCache.get("Achievement-OpinionLeader")).thenReturn(achievement);

        assertDoesNotThrow(() -> handler.handle(dto));

    }

    @Test
    void testHandleAchievement_CurrentPointsAboveThreshold() {
        long currentPointsValue = achievement.getPoints() + 1;
        progress = AchievementProgress.builder()
                .userId(1L)
                .achievement(achievement)
                .currentPoints(currentPointsValue)
                .build();

        handler.handle(dto);

        verify(achievementService, never()).giveAchievement(anyLong(), any(Achievement.class));
    }
}