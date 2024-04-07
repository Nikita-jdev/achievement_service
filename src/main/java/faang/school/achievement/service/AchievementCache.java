package faang.school.achievement.service;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AchievementCache {
    private final Map<String, Achievement> titleAchievement = new HashMap<>();
    private final AchievementRepository achievementRepository;
    private final AchievementMapper achievementMapper;

    public AchievementDto get(String title) {
        return achievementMapper.toDto(titleAchievement.get(title));
    }

    @PostConstruct
    private void cacheLoad() {
        achievementRepository.findAll().forEach(
                achievement -> titleAchievement.put(achievement.getTitle(), achievement)
        );
    }
}