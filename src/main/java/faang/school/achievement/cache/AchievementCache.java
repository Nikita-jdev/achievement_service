package faang.school.achievement.cache;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AchievementCache {

    private static final Map<String, Achievement> ACHIEVEMENT_CACHE = new HashMap<>();
    private final AchievementRepository achievementRepository;

    @PostConstruct
    public void init() {
        achievementRepository.findAll()
                .forEach(achievement -> ACHIEVEMENT_CACHE.put(achievement.getTitle(), achievement));
    }

    public Achievement get(String title) {
        return ACHIEVEMENT_CACHE.get(title);
    }
}