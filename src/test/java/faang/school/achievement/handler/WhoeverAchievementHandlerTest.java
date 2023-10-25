package faang.school.achievement.handler;

import faang.school.achievement.dto.SkillAcquiredEventDto;
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
public class WhoeverAchievementHandlerTest {
    @Mock
    private AchievementService achievementService;
    @Mock
    private UserAchievementService userAchievementService;
    @Mock
    private AchievementProgressService achievementProgressService;

    private final String titleAchievement = "Скиловый перец";

    private Achievement achievement;
    private SkillAcquiredEventDto skillAcquiredEventDto;
    private AchievementProgress achievementProgress;
    @InjectMocks
    private WhoeverAchievementHandler whoeverAchievementHandler =
            new WhoeverAchievementHandler(achievementService, userAchievementService, achievementProgressService, titleAchievement);

    @BeforeEach
    public void init() {
        achievement = Achievement.builder()
                .id(1L)
                .title(titleAchievement)
                .rarity(Rarity.RARE)
                .build();

        skillAcquiredEventDto = SkillAcquiredEventDto.builder()
                .skillId(7L)
                .receiverId(9L)
                .build();

        achievementProgress = AchievementProgress.builder()
                .id(1L)
                .achievement(achievement)
                .userId(2L)
                .currentPoints(9)
                .version(1L)
                .build();
    }

    @Test
    void testHandler() {
        Mockito.when(achievementService.getAchievement(titleAchievement)).thenReturn(achievement);

        Mockito.when(userAchievementService.hasAchievement(skillAcquiredEventDto.getReceiverId(), achievement.getId())).thenReturn(false);

        Mockito.when(achievementProgressService.getProgress(skillAcquiredEventDto.getReceiverId(), achievement.getId()))
                .thenReturn(achievementProgress);

        achievementProgress.increment();

        Mockito.when(achievementProgressService.updateProgress(achievementProgress)).thenReturn(achievementProgress);
        Mockito.when(userAchievementService.hasAchievement(skillAcquiredEventDto.getReceiverId(), achievement.getId())).thenReturn(false);

        whoeverAchievementHandler.handle(skillAcquiredEventDto.getReceiverId());

        Mockito.verify(userAchievementService, Mockito.times(1))
                .hasAchievement(skillAcquiredEventDto.getReceiverId(), achievement.getId());
    }
}
