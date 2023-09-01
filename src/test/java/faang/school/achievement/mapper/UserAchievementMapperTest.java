package faang.school.achievement.mapper;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.UserAchievementDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.Rarity;
import faang.school.achievement.model.UserAchievement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserAchievementMapperTest {

    private AchievementMapper achievementMapper = new AchievementMapperImpl();
    private UserAchievementMapper userAchievementMapper = new UserAchievementMapperImpl(achievementMapper);

    private Achievement firstAchievement;
    private Achievement secondAchievement;

    private UserAchievement firstUserAchievement;
    private UserAchievement secondUserAchievement;

    private AchievementDto firstAchievementDto;
    private AchievementDto secondAchievementDto;

    private UserAchievementDto firstUserAchievementDto;
    private UserAchievementDto secondUserAchievementDto;

    private LocalDateTime currentTime;

    @BeforeEach
    void setUp() {
        currentTime = LocalDateTime.now();
        firstAchievement = Achievement.builder()
                .id(1)
                .title("first title")
                .description("first description")
                .rarity(Rarity.COMMON)
                .points(100)
                .build();
        firstUserAchievement = UserAchievement.builder()
                .id(1)
                .achievement(firstAchievement)
                .userId(1)
                .createdAt(currentTime)
                .build();
        secondAchievement = Achievement.builder()
                .id(2)
                .title("second title")
                .description("second description")
                .rarity(Rarity.RARE)
                .points(200)
                .build();
        secondUserAchievement = UserAchievement.builder()
                .id(2)
                .achievement(secondAchievement)
                .userId(2)
                .createdAt(currentTime)
                .build();
        firstAchievementDto = AchievementDto.builder()
                .id(1)
                .title("first title")
                .description("first description")
                .rarity(Rarity.COMMON)
                .points(100)
                .build();
        firstUserAchievementDto = UserAchievementDto.builder()
                .id(1)
                .achievementDto(firstAchievementDto)
                .userId(1)
                .receivedAt(currentTime)
                .build();
        secondAchievementDto = AchievementDto.builder()
                .id(2)
                .title("second title")
                .description("second description")
                .rarity(Rarity.RARE)
                .points(200)
                .build();
        secondUserAchievementDto = UserAchievementDto.builder()
                .id(2)
                .achievementDto(secondAchievementDto)
                .userId(2)
                .receivedAt(currentTime)
                .build();
    }

    @Test
    void toDtoTest() {
        UserAchievementDto result = userAchievementMapper.toDto(firstUserAchievement);

        assertEquals(firstUserAchievementDto, result);
    }

    @Test
    void toDtoList() {
        List<UserAchievementDto> expected = List.of(firstUserAchievementDto, secondUserAchievementDto);

        List<UserAchievementDto> result = userAchievementMapper.toDtoList(List.of(firstUserAchievement, secondUserAchievement));

        assertEquals(expected, result);
    }

    @Test
    void toEntity() {
        UserAchievement result = userAchievementMapper.toEntity(firstUserAchievementDto);

        assertEquals(firstUserAchievement, result);
    }

    @Test
    void toEntityList() {
        List<UserAchievement> expected = List.of(firstUserAchievement, secondUserAchievement);

        List<UserAchievement> result = userAchievementMapper.toEntityList(List.of(firstUserAchievementDto, secondUserAchievementDto));

        assertEquals(expected, result);
    }
}