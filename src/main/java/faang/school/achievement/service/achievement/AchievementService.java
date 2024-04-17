package faang.school.achievement.service.achievement;

import faang.school.achievement.exception.EntityNotFoundException;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AchievementService {
    private final UserAchievementRepository userAchievementRepository;
    private final AchievementProgressRepository achievementProgressRepository;
    @Transactional
    public void createProgressIfNecessary(long userId, long achievementId){
        achievementProgressRepository.createProgressIfNecessary(userId, achievementId);
    }
    @Transactional(readOnly = true)
    public AchievementProgress getProgress(long userId, long achievementId){
       return achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId)
               .orElseThrow(() -> new EntityNotFoundException("Achievement progress not found"));
    }
    @Transactional(readOnly = true)
    public boolean hasAchievement(long userId, long achievementId){
       return userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId);
    }
    @Transactional
    public void giveAchievement(long userId, Achievement achievement){
        userAchievementRepository.save(UserAchievement.builder()
                .userId(userId)
                .achievement(achievement)
                .build());
    }

}
