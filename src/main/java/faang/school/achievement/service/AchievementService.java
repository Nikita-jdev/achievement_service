package faang.school.achievement.service;

import faang.school.achievement.exception.AchievementProgressNotFoundException;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AchievementService {

    private final UserAchievementRepository userAchievementRepository;
    private final AchievementProgressRepository achievementProgressRepository;

    public boolean hasAchievement(long userId, long achievementId) {
        log.info("Проверка наличия achievement для userId: {}, achievementId: {}",
                userId, achievementId);
        return userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId);
    }

    public void createProgressIfNecessary(long userId, long achievementId) {
        log.info("Создание прогресса achievement для userId: {}, achievementId: {}",
                userId, achievementId);
        achievementProgressRepository.createProgressIfNecessary(userId, achievementId);
    }

    public Optional<AchievementProgress> getProgress(long userId, long achievementId) {
        log.info("Получение прогресса achievement для userId: {}, achievementId: {}",
                userId, achievementId);
        return achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId);
    }

    @Transactional
    public AchievementProgress createProgressIfNecessaryOrGet(long userId, long achievementId) {
        createProgressIfNecessary(userId, achievementId);
        log.info("Создание или получение прогресса achievement для userId: {}, achievementId: {}",
                userId, achievementId);
        return getProgress(userId, achievementId)
                .orElseThrow(() -> new AchievementProgressNotFoundException("Прогресс для  userID: "
                        + userId + " и achievementID: " + achievementId + " не найдено."));
    }

    @Transactional
    public AchievementProgress incrementProgress(AchievementProgress achievementProgress) {
        log.info("Увеличение прогресса achievement для userId: {}, achievementId: {}",
                achievementProgress.getUserId(), achievementProgress.getAchievement().getId());
        achievementProgress.increment();
        return achievementProgressRepository.save(achievementProgress);
    }

    // @Async("taskExecutor") в основном майне добавил принудительное использование CGLIB
    public UserAchievement giveAchievement(long userId, Achievement achievement) {
        log.info("Получен запрос на получение achievement для userId: {}, achievementId: {}",
                userId, achievement.getId());
        UserAchievement userAchievement = UserAchievement.builder()
                .userId(userId)
                .achievement(achievement)
                .build();
        return userAchievementRepository.save(userAchievement);
    }
}