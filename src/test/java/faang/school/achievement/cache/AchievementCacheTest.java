package faang.school.achievement.cache;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.Rarity;
import faang.school.achievement.repository.AchievementRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AchievementCacheTest {
    @InjectMocks
    private AchievementCache achievementCache;

    @Mock
    private AchievementRepository achievementRepository;

    @Test
    public void testInit() throws NoSuchFieldException {
        Achievement achievement1 = Achievement.builder().title("Title 1")
                .description("Description1")
                .rarity(Rarity.COMMON)
                .points(2L).build();
        Achievement achievement2 = Achievement.builder().title("Title 2")
                .description("Description2")
                .rarity(Rarity.COMMON)
                .points(2L).build();
        List<Achievement> achievements = Arrays.asList(achievement1, achievement2);

        when(achievementRepository.findAll()).thenReturn(achievements);

        achievementCache.init();

        Field achievementsField = AchievementCache.class.getDeclaredField("achievements");
        achievementsField.setAccessible(true);
        Map<String, Achievement> cachedAchievements = achievements.stream().collect(Collectors.toMap(Achievement::getTitle, Function.identity()));
        assertEquals(2, cachedAchievements.size());
        assertTrue(cachedAchievements.containsKey("Title 1"));
        assertTrue(cachedAchievements.containsKey("Title 2"));
    }

    @Test
    public void testGetExistingAchievement() throws NoSuchFieldException, IllegalAccessException {
        Achievement achievement = Achievement.builder().title("Title")
                .description("Description")
                .rarity(Rarity.COMMON)
                .points(2L).build();
        Map<String, Achievement> cachedAchievements = new HashMap<>();
        cachedAchievements.put("Title", achievement);
        setPrivateField(achievementCache, "achievements", cachedAchievements);

        Optional<Achievement> result = achievementCache.get("Title");

        assertTrue(result.isPresent());
        assertEquals(achievement, result.get());
    }

    @Test
    public void testGetNonExistingAchievement() {
        Optional<Achievement> result = achievementCache.get("Non-existent Title");

        assertFalse(result.isPresent());
    }

    private void setPrivateField(Object object, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }

}
