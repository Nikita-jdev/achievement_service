package faang.school.achievement.mapper;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.Rarity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AchievementProgressMapperTest {

    private AchievementMapper achievementMapper = new AchievementMapperImpl();
    private AchievementProgressMapper achievementProgressMapper = new AchievementProgressMapperImpl(achievementMapper);

    private Achievement firstAchievement;
    private Achievement secondAchievement;

    private AchievementProgress firstProgress;
    private AchievementProgress secondProgress;

    private AchievementDto firstAchievementDto;
    private AchievementDto secondAchievementDto;

    private AchievementProgressDto firstDto;
    private AchievementProgressDto secondDto;

    @BeforeEach
    void setUp() {
        firstAchievement = Achievement.builder()
                .id(1)
                .title("first title")
                .description("first description")
                .rarity(Rarity.COMMON)
                .points(100)
                .build();
        firstProgress = AchievementProgress.builder()
                .id(1)
                .achievement(firstAchievement)
                .userId(1)
                .currentPoints(35)
                .build();
        secondAchievement = Achievement.builder()
                .id(2)
                .title("second title")
                .description("second description")
                .rarity(Rarity.RARE)
                .points(200)
                .build();
        secondProgress = AchievementProgress.builder()
                .id(2)
                .achievement(secondAchievement)
                .userId(2)
                .currentPoints(55)
                .build();
        firstAchievementDto = AchievementDto.builder()
                .id(1)
                .title("first title")
                .description("first description")
                .rarity(Rarity.COMMON)
                .points(100)
                .build();
        firstDto = AchievementProgressDto.builder()
                .id(1)
                .achievementDto(firstAchievementDto)
                .userId(1)
                .currentPoints(35)
                .build();
        secondAchievementDto = AchievementDto.builder()
                .id(2)
                .title("second title")
                .description("second description")
                .rarity(Rarity.RARE)
                .points(200)
                .build();
        secondDto = AchievementProgressDto.builder()
                .id(2)
                .achievementDto(secondAchievementDto)
                .userId(2)
                .currentPoints(55)
                .build();
    }

    @Test
    void toDtoTest() {
        AchievementProgressDto result = achievementProgressMapper.toDto(firstProgress);

        assertEquals(firstDto, result);
    }

    @Test
    void toDtoList() {
        List<AchievementProgressDto> expected = List.of(firstDto, secondDto);

        List<AchievementProgressDto> result = achievementProgressMapper.toDtoList(List.of(firstProgress, secondProgress));

        assertEquals(expected, result);
    }

    @Test
    void toEntity() {
        AchievementProgress result = achievementProgressMapper.toEntity(firstDto);

        assertEquals(firstProgress, result);
    }

    @Test
    void toEntityList() {
        List<AchievementProgress> expected = List.of(firstProgress, secondProgress);

        List<AchievementProgress> result = achievementProgressMapper.toEntityList(List.of(firstDto, secondDto));

        assertEquals(expected, result);
    }
}