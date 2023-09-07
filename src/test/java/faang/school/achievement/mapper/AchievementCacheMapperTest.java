package faang.school.achievement.mapper;

import faang.school.achievement.dto.AchievementCacheDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.Rarity;
import faang.school.achievement.model.UserAchievement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AchievementCacheMapperTest {

    private AchievementCacheMapper achievementCacheMapper = new AchievementCacheMapperImpl();

    private AchievementCacheDto achievementCacheDto;
    private Achievement achievement;
    private UserAchievement userAchievement;
    private AchievementProgress achievementProgress;

    @BeforeEach
    void setUp() {
        LocalDateTime currentTime = LocalDateTime.now();
        userAchievement = UserAchievement.builder()
                .id(1)
                .build();
        achievementProgress = AchievementProgress.builder()
                .id(1)
                .build();
        achievementCacheDto = AchievementCacheDto.builder()
                .id(1)
                .title("Some title")
                .description("Some descriptions")
                .rarity(Rarity.RARE)
                .userAchievements(List.of(1L))
                .progresses(List.of(1L))
                .points(1000)
                .createdAt(currentTime)
                .updatedAt(currentTime)
                .build();
        achievement = Achievement.builder()
                .id(1)
                .title("Some title")
                .description("Some descriptions")
                .rarity(Rarity.RARE)
                .userAchievements(List.of(userAchievement))
                .progresses(List.of(achievementProgress))
                .points(1000)
                .createdAt(currentTime)
                .updatedAt(currentTime)
                .build();
    }

    @Test
    void toDto() {
        AchievementCacheDto result = achievementCacheMapper.toDto(achievement);

        assertEquals(achievementCacheDto, result);
    }

    @Test
    void toEntity() {
        Achievement result = achievementCacheMapper.toEntity(achievementCacheDto);

        assertEquals(achievement, result);
    }

    @Test
    void toDtoAchievement() {
        List<UserAchievement> userAchievements = List.of(userAchievement);

        List<Long> result = achievementCacheMapper.toDtoAchievement(userAchievements);

        assertEquals(List.of(1L), result);
    }

    @Test
    void toDtoProgress() {
        List<AchievementProgress> achievementProgresses = List.of(achievementProgress);

        List<Long> result = achievementCacheMapper.toDtoProgress(achievementProgresses);

        assertEquals(List.of(1L), result);
    }

    @Test
    void toEntityAchievement() {
        List<Long> userAchievementIds = List.of(1L);

        List<UserAchievement> result = achievementCacheMapper.toEntityAchievement(userAchievementIds);

        assertEquals(List.of(userAchievement), result);
    }

    @Test
    void toEntityProgress() {
        List<Long> achievementProgressIds = List.of(1L);

        List<AchievementProgress> result = achievementCacheMapper.toEntityProgress(achievementProgressIds);

        assertEquals(List.of(achievementProgress), result);
    }
}
