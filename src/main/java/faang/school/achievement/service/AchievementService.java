package faang.school.achievement.service;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementEventDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.dto.UserAchievementDto;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.mapper.AchievementProgressMapper;
import faang.school.achievement.mapper.UserAchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.publisher.AchievementPublisher;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AchievementService {

    private final AchievementPublisher achievementPublisher;
    private final AchievementProgressRepository progressRepository;
    private final AchievementRepository achievementRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final AchievementProgressMapper achievementProgressMapper;
    private final AchievementMapper achievementMapper;
    private final UserAchievementMapper userAchievementMapper;
    private final AchievementCache achievementCache;

    public AchievementDto getAchievementByTitle(String title) {
        Achievement achievement = getAchievement(title);
        return achievementMapper.toAchievementDto(achievement);
    }

    public List<AchievementDto> getAllAchievements(Pageable pageable) {
        Page<Achievement> page = achievementRepository.findAll(pageable);
        return page.getContent()
                .stream()
                .map(achievementMapper::toAchievementDto)
                .toList();
    }

    public List<UserAchievementDto> getUserAchievements(long userId) {
        List<UserAchievement> achievements = userAchievementRepository.findByUserId(userId);
        return userAchievementMapper.toDtoList(achievements);
    }

    public List<AchievementProgressDto> getAchievementsProgressByUserId(long userId) {
        List<AchievementProgress> achievementProgresses = progressRepository.findByUserId(userId);
        return achievementProgressMapper.toDtoList(achievementProgresses);
    }

    public AchievementProgressDto getAchievementProgressByUserId(long achievementId, long userId) {
        AchievementProgress achievementProgress = getUserProgressByAchievementAndUserId(achievementId, userId);
        return achievementProgressMapper.toDto(achievementProgress);
    }

    public boolean hasAchievement(long authorId, long achievementId) {
        return userAchievementRepository.existsByUserIdAndAchievementId(authorId, achievementId);
    }

    public long getProgress(long authorId, long achievementId) {
        AchievementProgress progress = getUserProgressByAchievementAndUserId(achievementId, authorId);
        progress.increment();
        log.info("Achievement progress for authorId:{} has incremented successfully", authorId);
        progressRepository.save(progress);
        return progress.getCurrentPoints();
    }

    public void checkAndCreateAchievementProgress(long userId, long achievementId) {
        Optional<AchievementProgress> userProgress = progressRepository.findByUserIdAndAchievementId(userId, achievementId);
        if (userProgress.isEmpty()) {
            progressRepository.createProgressIfNecessary(userId, achievementId);
        }
    }

    @Transactional
    public UserAchievement giveAchievement(long authorId, String achievementTitle) {
        Achievement currentAchievement = getAchievement(achievementTitle);
        UserAchievement userAchievement = UserAchievement.builder()
                .userId(authorId)
                .achievement(currentAchievement)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        userAchievementRepository.save(userAchievement);

        long id = currentAchievement.getId();
        String title = currentAchievement.getTitle();

        achievementPublisher.publish(new AchievementEventDto(id, authorId, title, LocalDateTime.now()));
        log.info("Achievement:{} for authorId:{} saved successfully", currentAchievement.getTitle(), authorId);
        return userAchievement;
    }

    public Achievement getAchievement(String title) {
        return achievementCache.get(title)
                .or(() -> achievementRepository.findByTitle(title))
                .orElseThrow(() -> new EntityNotFoundException(String.format("There is no achievement named: %s", title)));
    }

    public AchievementProgress getUserProgressByAchievementAndUserId(long achievementId, long userId) {
        return progressRepository.findByUserIdAndAchievementId(userId, achievementId)
                .orElseGet(() -> createAndGetAchievementProgress(achievementId, userId));
    }

    public AchievementProgress createAndGetAchievementProgress(long achievementId, long userId) {
        progressRepository.createProgressIfNecessary(userId, achievementId);
        return progressRepository.findByUserIdAndAchievementId(userId, achievementId).get();
    }
}