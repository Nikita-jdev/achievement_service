package faang.school.achievement.mapper;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.Rarity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AchievementMapperTest {

    private AchievementMapper achievementMapper = new AchievementMapperImpl();

    private Achievement achievement;
    private AchievementDto achievementDto;

    @BeforeEach
    void setUp() {
        achievement = Achievement.builder()
                .id(1)
                .title("first title")
                .description("first description")
                .rarity(Rarity.COMMON)
                .points(35)
                .build();
        achievementDto = AchievementDto.builder()
                .id(1)
                .title("first title")
                .description("first description")
                .rarity(Rarity.COMMON)
                .points(35)
                .build();
    }

    @Test
    void toDtoTest() {
        AchievementDto result = achievementMapper.toAchievementDto(achievement);

        assertEquals(achievementDto, result);
    }

    @Test
    void toEntityTest() {
        Achievement result = achievementMapper.toAchievementEntity(achievementDto);

        assertEquals(achievement, result);
    }
}