package faang.school.achievement.cache;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AchievementCache {
    private Map<String, Achievement> achievements = new HashMap<>();
    private final AchievementRepository achievementRepository;

    @PostConstruct
    public void init(){
        achievementRepository.findAll().forEach(achievement -> achievements.put(achievement.getTitle(), achievement));
    }

    public Achievement get(String achievementTitle) {
        return achievements.get(achievementTitle);
    }
}
