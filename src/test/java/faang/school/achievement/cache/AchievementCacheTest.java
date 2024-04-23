package faang.school.achievement.cache;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AchievementCacheTest {
    @Mock
    private AchievementRepository achievementRepository;

    @InjectMocks
    private AchievementCache achievementCache;

    @Test
    void goalAchievementCacheTest() {
        HashMap<String, Achievement> achievementHashMap = new HashMap<>();
        Achievement firstAchievement = Achievement.builder()
                .title("firstAchievement")
                .build();
        Achievement secondAchievement = Achievement.builder()
                .title("secondAchievement")
                .build();

        List<Achievement> achievementsList = List.of(firstAchievement, secondAchievement);
        achievementsList.forEach(achievement -> achievementHashMap.put(achievement.getTitle(), achievement));
        when(achievementRepository.findAll()).thenReturn(List.of(firstAchievement, secondAchievement));

        assertDoesNotThrow(() -> achievementCache.goalAchievementCache());
    }

    @Test
    void getAchievementTest() throws IllegalAccessException, NoSuchFieldException {
        HashMap<String, Achievement> achievementHashMap = new HashMap<>();
        Achievement firstAchievement = Achievement.builder()
                .title("firstAchievement")
                .build();
        Achievement secondAchievement = Achievement.builder()
                .title("secondAchievement")
                .build();
        achievementHashMap.put(firstAchievement.getTitle(), firstAchievement);
        achievementHashMap.put(secondAchievement.getTitle(), secondAchievement);
        Field field = achievementCache.getClass().getDeclaredField("achievementHash");
        field.setAccessible(true);
        field.set(achievementCache, achievementHashMap);

        assertEquals(firstAchievement, achievementCache.getAchievement(firstAchievement.getTitle()).get());
    }
}