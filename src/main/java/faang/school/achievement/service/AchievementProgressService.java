package faang.school.achievement.service;


import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.exception.AchievementProgressCreationException;
import faang.school.achievement.mapper.AchievementProgressMapper;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.repository.AchievementProgressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AchievementProgressService {

    private final AchievementProgressMapper achievementProgressMapper;
    private final AchievementProgressRepository progressRepository;

    public AchievementProgressDto getAchievementProgressByUserId(long achievementId, long userId) {
        AchievementProgress achievementProgress = getUserProgressByAchievementAndUserId(userId, achievementId);
        return achievementProgressMapper.toDto(achievementProgress);
    }

    public Page<AchievementProgressDto> getAchievementsProgressByUserId(long userId) {
        List<AchievementProgress> achievementProgresses = progressRepository.findByUserId(userId);
        List<AchievementProgressDto> progressDtos = achievementProgressMapper.toDtoList(achievementProgresses);
        return new PageImpl<>(progressDtos);
    }

    public AchievementProgress getUserProgressByAchievementAndUserId(long userId, long achievementId) {
        return progressRepository.findByUserIdAndAchievementId(userId, achievementId)
                .orElseGet(() -> createAndGetAchievementProgress(userId, achievementId));
    }

    private AchievementProgress createAndGetAchievementProgress(long userId, long achievementId) {
        progressRepository.createProgressIfNecessary(userId, achievementId);
        return progressRepository.findByUserIdAndAchievementId(userId, achievementId)
                .orElseThrow(() -> new AchievementProgressCreationException(String.format("Can't create achievement progress with userId: %d, achievementId: %d", userId, achievementId)));
    }
}
