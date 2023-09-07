package faang.school.achievement.service;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.mapper.AchievementMapperImpl;
import faang.school.achievement.mapper.AchievementProgressMapper;
import faang.school.achievement.mapper.AchievementProgressMapperImpl;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.Rarity;
import faang.school.achievement.repository.AchievementProgressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AchievementProgressServiceTest {

    @Spy
    private AchievementMapper achievementMapper = new AchievementMapperImpl();
    @Spy
    private AchievementProgressMapper achievementProgressMapper = new AchievementProgressMapperImpl(achievementMapper);
    @Mock
    private AchievementProgressRepository progressRepository;

    @InjectMocks
    private AchievementProgressService achievementProgressService;

    private AchievementProgress controllerAchievementProgress;
    private AchievementProgressDto controllerAchievementProgressDto;

    private final long ACHIEVEMENT_ID = 1L;
    private final long AUTHOR_ID = 1L;

    @BeforeEach
    void setUp() {
        LocalDateTime createdAt = LocalDateTime.now().minusMonths(1);
        LocalDateTime updatedAt = LocalDateTime.now();
        String ACHIEVEMENT_TITTLE = "subscribers";
        Achievement controllerAchievement = Achievement.builder()
                .id(ACHIEVEMENT_ID)
                .title(ACHIEVEMENT_TITTLE)
                .description("Must be a leader!")
                .rarity(Rarity.RARE)
                .points(500)
                .build();
        AchievementDto achievementDto = AchievementDto.builder()
                .id(ACHIEVEMENT_ID)
                .title(ACHIEVEMENT_TITTLE)
                .description("Must be a leader!")
                .rarity(Rarity.RARE)
                .points(500)
                .build();
        controllerAchievementProgress = AchievementProgress.builder()
                .id(1)
                .achievement(controllerAchievement)
                .userId(2)
                .currentPoints(100)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
        controllerAchievementProgressDto = AchievementProgressDto.builder()
                .id(1)
                .achievementDto(achievementDto)
                .userId(2)
                .currentPoints(100)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    @Test
    void getAchievementProgressByUserIdFirstScenarioTestTest() {
        when(progressRepository.findByUserIdAndAchievementId(AUTHOR_ID, ACHIEVEMENT_ID))
                .thenReturn(Optional.of(controllerAchievementProgress));

        AchievementProgressDto result = achievementProgressService.getAchievementProgressByUserId(ACHIEVEMENT_ID, AUTHOR_ID);

        assertEquals(controllerAchievementProgressDto, result);
    }

    @Test
    void getAchievementProgressByUserIdSecondScenarioTestTest() {
        doReturn(Optional.empty()).doReturn(Optional.of(controllerAchievementProgress))
                .when(progressRepository).findByUserIdAndAchievementId(AUTHOR_ID, ACHIEVEMENT_ID);

        AchievementProgressDto result = achievementProgressService.getAchievementProgressByUserId(ACHIEVEMENT_ID, AUTHOR_ID);

        assertEquals(controllerAchievementProgressDto, result);

        verify(progressRepository).createProgressIfNecessary(AUTHOR_ID, ACHIEVEMENT_ID);
    }

    @Test
    void getAchievementsProgressByUserIdTest() {
        List<AchievementProgress> achievementProgresses = List.of(controllerAchievementProgress);

        when(progressRepository.findByUserId(AUTHOR_ID)).thenReturn(achievementProgresses);

        Page<AchievementProgressDto> result = achievementProgressService.getAchievementsProgressByUserId(AUTHOR_ID);

        assertEquals(new PageImpl<>(List.of(controllerAchievementProgressDto)), result);
    }
}