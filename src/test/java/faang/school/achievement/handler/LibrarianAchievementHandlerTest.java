package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import faang.school.achievement.service.AchievementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Retryable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class LibrarianAchievementHandlerTest {

    @Mock
    private AchievementService achievementService;

    @Mock
    private AchievementCache achievementCache;

    @Mock
    private UserAchievementRepository userAchievementRepository;

    @Mock
    private AchievementProgressRepository achievementProgressRepository;

    @InjectMocks
    private LibrarianAchievementHandler librarianAchievementHandler;
    long userId;
    String achievementTitle;
    long achievementId;
    Achievement achievement;
    AchievementProgress progress;
    @BeforeEach
    void setUp(){
        userId = 1;
        achievementTitle = "LIBRARIAN";
        achievementId = 5;
        achievement = Achievement.builder()
                .title(achievementTitle)
                .id(achievementId)
                .build();
        progress = new AchievementProgress();
    }

    @Test
    void testHandleAchievementReceivedSuccessful() {
        when(achievementCache.getAchievement(achievementTitle)).thenReturn(achievement);
        progress.setCurrentPoints(10);
        achievement.setPoints(10);
        when(achievementService.hasAchievement(userId, achievement.getId())).thenReturn(false);
        when(achievementService.getProgress(userId, achievementId)).thenReturn(progress);
        when(achievementProgressRepository.save(progress)).thenReturn(progress);

        librarianAchievementHandler.handleAbstract(userId, achievementTitle);

        verify(achievementCache).getAchievement(achievementTitle);
        verify(achievementService).hasAchievement(userId, achievement.getId());
        verify(achievementService).getProgress(userId, achievement.getId());
        verify(achievementService).createProgressIfNecessary(userId, achievement.getId());
        verify(achievementProgressRepository).save(any());
    }
}