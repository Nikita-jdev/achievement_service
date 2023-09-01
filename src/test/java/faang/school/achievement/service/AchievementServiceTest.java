package faang.school.achievement.service;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementEventDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.dto.UserAchievementDto;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.mapper.AchievementMapperImpl;
import faang.school.achievement.mapper.AchievementProgressMapper;
import faang.school.achievement.mapper.AchievementProgressMapperImpl;
import faang.school.achievement.mapper.UserAchievementMapper;
import faang.school.achievement.mapper.UserAchievementMapperImpl;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.Rarity;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.publisher.AchievementPublisher;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AchievementServiceTest {

    @Mock
    private AchievementPublisher achievementPublisher;
    @Mock
    private AchievementProgressRepository progressRepository;
    @Mock
    private AchievementRepository achievementRepository;
    @Mock
    private UserAchievementRepository userAchievementRepository;
    @Spy
    private AchievementMapper achievementMapper = new AchievementMapperImpl();
    @Spy
    private AchievementProgressMapper achievementProgressMapper = new AchievementProgressMapperImpl(achievementMapper);
    @Spy
    private UserAchievementMapper userAchievementMapper = new UserAchievementMapperImpl(achievementMapper);
    @Mock
    private AchievementCache achievementCache;
    @InjectMocks
    private AchievementService achievementService;

    private AchievementProgress achievementProgress;
    private AchievementProgress controllerAchievementProgress;
    private AchievementProgressDto controllerAchievementProgressDto;
    private Achievement achievement;
    private Achievement controllerAchievement;
    private Achievement secondAchievement;
    private AchievementDto achievementDto;
    private AchievementDto secondAchievementDto;
    private final long ACHIEVEMENT_ID = 1L;
    private final long AUTHOR_ID = 1L;
    private final long CURRENT_POINTS = 999L;
    private final String ACHIEVEMENT_TITTLE = "Opinion leader";

    @BeforeEach
    void initData() {
        achievementProgress = AchievementProgress.builder()
                .userId(AUTHOR_ID)
                .currentPoints(CURRENT_POINTS)
                .build();
        achievement = Achievement.builder()
                .id(ACHIEVEMENT_ID)
                .title(ACHIEVEMENT_TITTLE)
                .build();
        controllerAchievement = Achievement.builder()
                .id(ACHIEVEMENT_ID)
                .title(ACHIEVEMENT_TITTLE)
                .description("Must be a leader!")
                .rarity(Rarity.RARE)
                .points(500)
                .build();
        achievementDto = AchievementDto.builder()
                .id(ACHIEVEMENT_ID)
                .title(ACHIEVEMENT_TITTLE)
                .description("Must be a leader!")
                .rarity(Rarity.RARE)
                .points(500)
                .build();
        secondAchievement = Achievement.builder()
                .id(2)
                .title("subscribers")
                .description("Must have 1000 subscribers")
                .rarity(Rarity.EPIC)
                .points(1000)
                .build();
        secondAchievementDto = AchievementDto.builder()
                .id(2)
                .title("subscribers")
                .description("Must have 1000 subscribers")
                .rarity(Rarity.EPIC)
                .points(1000)
                .build();
        controllerAchievementProgress = AchievementProgress.builder()
                .id(1)
                .achievement(controllerAchievement)
                .userId(2)
                .currentPoints(100)
                .build();
        controllerAchievementProgressDto = AchievementProgressDto.builder()
                .id(1)
                .achievementDto(achievementDto)
                .userId(2)
                .currentPoints(100)
                .build();
    }

    @Test
    void getAchievementByTitleTest() {
        when(achievementCache.get(ACHIEVEMENT_TITTLE)).thenReturn(Optional.of(controllerAchievement));

        AchievementDto result = achievementService.getAchievementByTitle(ACHIEVEMENT_TITTLE);

        assertEquals(achievementDto, result);
    }

    @Test
    void getAllAchievementsTest() {
        Pageable pageable = PageRequest.of(0, 2);

        List<Achievement> achievements = List.of(controllerAchievement, secondAchievement);
        List<AchievementDto> expected = List.of(achievementDto, secondAchievementDto);

        Page<Achievement> achievementPage = new PageImpl<>(achievements, pageable, achievements.size());

        when(achievementRepository.findAll(pageable)).thenReturn(achievementPage);

        List<AchievementDto> result = achievementService.getAllAchievements(pageable);

        assertEquals(expected, result);
    }

    @Test
    void getUserAchievements() {
        LocalDateTime currentTime = LocalDateTime.now();

        UserAchievement userAchievement = UserAchievement.builder()
                .id(1)
                .achievement(controllerAchievement)
                .userId(2)
                .createdAt(currentTime)
                .build();

        UserAchievementDto userAchievementDto = UserAchievementDto.builder()
                .id(1)
                .achievementDto(achievementDto)
                .userId(2)
                .receivedAt(currentTime)
                .build();

        when(userAchievementRepository.findByUserId(AUTHOR_ID)).thenReturn(List.of(userAchievement));

        List<UserAchievementDto> result = achievementService.getUserAchievements(AUTHOR_ID);

        assertEquals(List.of(userAchievementDto), result);
    }

    @Test
    void getAchievementsProgressByUserIdTest() {
        List<AchievementProgress> achievementProgresses = List.of(controllerAchievementProgress);

        when(progressRepository.findByUserId(AUTHOR_ID)).thenReturn(achievementProgresses);

        List<AchievementProgressDto> result = achievementService.getAchievementsProgressByUserId(AUTHOR_ID);

        assertEquals(List.of(controllerAchievementProgressDto), result);
    }

    @Test
    void getAchievementProgressByUserIdTest() {
        when(progressRepository.findByUserIdAndAchievementId(AUTHOR_ID, ACHIEVEMENT_ID))
                .thenReturn(Optional.of(controllerAchievementProgress));

        AchievementProgressDto result = achievementService.getAchievementProgressByUserId(ACHIEVEMENT_ID, AUTHOR_ID);

        assertEquals(controllerAchievementProgressDto, result);
    }

    @Test
    void testHasAchievement() {
        achievementService.hasAchievement(AUTHOR_ID, ACHIEVEMENT_ID);
        verify(userAchievementRepository).existsByUserIdAndAchievementId(AUTHOR_ID, ACHIEVEMENT_ID);
    }

    @Test
    void testGetProgressFirstScenario() {
        when(progressRepository.findByUserIdAndAchievementId(AUTHOR_ID, ACHIEVEMENT_ID))
                .thenReturn(Optional.ofNullable(achievementProgress));
        long actualProgress = achievementService.getProgress(AUTHOR_ID, ACHIEVEMENT_ID);

        assertEquals(1000L, actualProgress);
        verify(progressRepository).save(achievementProgress);
    }

    @Test
    void testGetProgressSecondScenario() {
        doReturn(Optional.empty()).doReturn(Optional.ofNullable(achievementProgress))
                .when(progressRepository).findByUserIdAndAchievementId(AUTHOR_ID, ACHIEVEMENT_ID);

        achievementService.getProgress(AUTHOR_ID, ACHIEVEMENT_ID);

        verify(progressRepository, times(2)).findByUserIdAndAchievementId(AUTHOR_ID, ACHIEVEMENT_ID);
        verify(progressRepository).createProgressIfNecessary(AUTHOR_ID, ACHIEVEMENT_ID);
    }

    @Test
    void checkAndCreateAchievementProgressTest() {
        when(progressRepository.findByUserIdAndAchievementId(AUTHOR_ID, ACHIEVEMENT_ID))
                .thenReturn(Optional.empty());

        achievementService.checkAndCreateAchievementProgress(AUTHOR_ID, ACHIEVEMENT_ID);

        verify(progressRepository).findByUserIdAndAchievementId(AUTHOR_ID, ACHIEVEMENT_ID);
        verify(progressRepository).createProgressIfNecessary(AUTHOR_ID, ACHIEVEMENT_ID);
    }

    @Test
    void giveAchievementTest() {
        when(achievementCache.get(ACHIEVEMENT_TITTLE)).thenReturn(Optional.empty());
        when(achievementRepository.findByTitle(ACHIEVEMENT_TITTLE)).thenReturn(Optional.of(achievement));

        achievementService.giveAchievement(1, ACHIEVEMENT_TITTLE);

        verify(userAchievementRepository).save(any(UserAchievement.class));
        verify(achievementPublisher)
                .publish(any(AchievementEventDto.class));
    }

    @Test
    void getAchievementTest() {
        when(achievementCache.get(ACHIEVEMENT_TITTLE)).thenReturn(Optional.empty());
        when(achievementRepository.findByTitle(ACHIEVEMENT_TITTLE)).thenReturn(Optional.of(achievement));

        Achievement result = achievementService.getAchievement(ACHIEVEMENT_TITTLE);

        assertEquals(achievement, result);
    }

    @Test
    void getAchievementThrowException() {
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> achievementService.getAchievement(ACHIEVEMENT_TITTLE));

        assertEquals("There is no achievement named: Opinion leader", exception.getMessage());
    }

    @Test
    void getUserProgressByAchievementAndUserIdTest() {
        doReturn(Optional.empty()).doReturn(Optional.of(achievementProgress))
                .when(progressRepository).findByUserIdAndAchievementId(AUTHOR_ID, ACHIEVEMENT_ID);

        AchievementProgress result = achievementService.getUserProgressByAchievementAndUserId(ACHIEVEMENT_ID, AUTHOR_ID);

        assertEquals(achievementProgress, result);

        verify(progressRepository, times(2)).findByUserIdAndAchievementId(AUTHOR_ID, ACHIEVEMENT_ID);
        verify(progressRepository).createProgressIfNecessary(AUTHOR_ID, ACHIEVEMENT_ID);
    }

    @Test
    void createAndGetAchievementProgressTest() {
        when(progressRepository.findByUserIdAndAchievementId(AUTHOR_ID, ACHIEVEMENT_ID))
                .thenReturn(Optional.of(achievementProgress));

        AchievementProgress result = achievementService.createAndGetAchievementProgress(ACHIEVEMENT_ID, AUTHOR_ID);

        assertEquals(achievementProgress, result);
    }
}