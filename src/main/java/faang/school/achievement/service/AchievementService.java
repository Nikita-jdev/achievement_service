package faang.school.achievement.service;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementEventDto;
import faang.school.achievement.dto.UserAchievementDto;
import faang.school.achievement.mapper.AchievementMapper;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AchievementService {

    private final AchievementPublisher achievementPublisher;
    private final AchievementProgressRepository progressRepository;
    private final AchievementRepository achievementRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final AchievementMapper achievementMapper;
    private final UserAchievementMapper userAchievementMapper;
    private final AchievementCache achievementCache;
    private final AchievementProgressService achievementProgressService;

    public AchievementDto getAchievementByTitle(String title) {
        Achievement achievement = getAchievement(title);
        return achievementMapper.toAchievementDto(achievement);
    }

    public Page<AchievementDto> getAllAchievements(Pageable pageable) {
        Page<Achievement> page = achievementRepository.findAll(pageable);
        List<AchievementDto> achievementDtos = page.getContent()
                .stream()
                .map(achievementMapper::toAchievementDto)
                .toList();
        return new PageImpl<>(achievementDtos);
    }

    public Page<UserAchievementDto> getUserAchievements(long userId) {
        List<UserAchievement> achievements = userAchievementRepository.findByUserId(userId);
        List<UserAchievementDto> userAchievementDto = userAchievementMapper.toDtoList(achievements);
        return new PageImpl<>(userAchievementDto);
    }

    public boolean hasAchievement(long authorId, long achievementId) {
        return userAchievementRepository.existsByUserIdAndAchievementId(authorId, achievementId);
    }

    @Transactional
    public long getProgress(long authorId, long achievementId) {
        AchievementProgress progress = achievementProgressService.getUserProgressByAchievementAndUserId(authorId, achievementId);
        progress.increment();
        log.info("Achievement progress for authorId:{} has incremented successfully", authorId);
        progressRepository.save(progress);
        return progress.getCurrentPoints();
    }

    @Transactional
    public UserAchievement giveAchievement(Achievement achievement, long authorId) {
        UserAchievement userAchievement = UserAchievement.builder()
                .userId(authorId)
                .achievement(achievement)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        userAchievementRepository.save(userAchievement);

        long id = achievement.getId();
        String title = achievement.getTitle();

        achievementPublisher.publish(new AchievementEventDto(id, authorId, title, LocalDateTime.now()));
        log.info("Achievement:{} for authorId:{} saved successfully", achievement.getTitle(), authorId);
        return userAchievement;
    }

    public Achievement getAchievement(String title) {
        return achievementCache.get(title)
                .or(() -> achievementRepository.findByTitle(title))
                .orElseThrow(() -> new EntityNotFoundException(String.format("There is no achievement named: %s", title)));
    }
}