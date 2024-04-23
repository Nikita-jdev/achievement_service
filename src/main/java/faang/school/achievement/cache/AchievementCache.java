package faang.school.achievement.cache;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AchievementCache {
    private final AchievementRepository achievementRepository;
    private HashMap<String, Achievement> achievementHash = new HashMap<>();

    public void goalAchievementCache() {
        achievementRepository.findAll().forEach(achievement -> achievementHash.put(achievement.getTitle(), achievement));
    }

    public Optional<Achievement> getAchievement(String achievementTitle) {
        return Optional.ofNullable(achievementHash.get(achievementTitle));
    }
}