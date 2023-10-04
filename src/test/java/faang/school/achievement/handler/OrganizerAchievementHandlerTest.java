package faang.school.achievement.handler;

import faang.school.achievement.dto.InviteSentEventDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.Rarity;
import faang.school.achievement.service.AchievementProgressService;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.UserAchievementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrganizerAchievementHandlerTest {
    @Mock
    private AchievementService achievementService;

    @Mock
    private UserAchievementService userAchievementService;

    @Mock
    private AchievementProgressService achievementProgressService;

    private final String titleAchievement = "Организатор";
    private Achievement achievementSaved;
    private AchievementProgress achievementProgressSaved;

    @InjectMocks
    private OrganizerAchievementHandler organizerAchievementHandler =
            new OrganizerAchievementHandler(achievementService, userAchievementService, achievementProgressService,
                    titleAchievement);

    private InviteSentEventDto inviteSentEventDto;

    @BeforeEach
    void setUp() {
        inviteSentEventDto = InviteSentEventDto.builder()
                .authorId(1L)
                .invitedId(2L)
                .projectId(1L)
                .build();

        achievementSaved = Achievement.builder()
                .id(1L)
                .title(titleAchievement)
                .rarity(Rarity.RARE)
                .build();

        achievementProgressSaved = AchievementProgress.builder()
                .id(1L)
                .achievement(achievementSaved)
                .userId(inviteSentEventDto.getAuthorId())
                .currentPoints(99)
                .build();
    }

    @Test
    void testHandle() {
        Mockito.when(achievementService.getAchievement(titleAchievement)).thenReturn(achievementSaved);
        Mockito.when(userAchievementService.hasAchievement(achievementSaved.getId(), inviteSentEventDto.getAuthorId()))
                .thenReturn(false);
        Mockito.when(achievementProgressService.getProgress(inviteSentEventDto.getAuthorId(), achievementSaved.getId()))
                .thenReturn(achievementProgressSaved);
        achievementProgressSaved.increment();
        Mockito.when(achievementProgressService.updateProgress(achievementProgressSaved)).thenReturn(achievementProgressSaved);

        organizerAchievementHandler.handle(inviteSentEventDto.getAuthorId());

        Mockito.verify(userAchievementService, Mockito.times(1))
                .hasAchievement(achievementSaved.getId(), inviteSentEventDto.getAuthorId());
    }
}