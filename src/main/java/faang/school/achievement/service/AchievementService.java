package faang.school.achievement.service;

import faang.school.achievement.exception.EntityNotFoundException;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AchievementService {
    private final AchievementRepository achievementRepository;

    public Achievement findById(long id) {
        return achievementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Achievement not found"));
    }
}
