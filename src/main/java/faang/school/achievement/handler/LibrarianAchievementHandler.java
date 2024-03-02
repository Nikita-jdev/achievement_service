package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.AlbumCreatedEventDto;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import faang.school.achievement.service.AchievementService;
import jakarta.persistence.OptimisticLockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LibrarianAchievementHandler extends AbstractEventHandler<AlbumCreatedEventDto> {
    private final String title;

    public LibrarianAchievementHandler(AchievementService achievementService,
                                       AchievementCache achievementCache,
                                       UserAchievementRepository userAchievementRepository,
                                       AchievementProgressRepository achievementProgressRepository,
                                       @Value("${achievement_service.achievement_librarian}") String title) {
        super(achievementService, achievementCache, userAchievementRepository, achievementProgressRepository);
        this.title = title;
    }

    @Retryable(maxAttempts = 3, value = {OptimisticLockException.class})
    @Override
    public void handle(Long userId) {
        handleAbstract(userId, title);
    }
}
