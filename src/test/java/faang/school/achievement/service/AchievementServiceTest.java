package faang.school.achievement.service;

import faang.school.achievement.exception.EntityNotFoundException;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AchievementServiceTest {
@Mock
private AchievementRepository achievementRepository;

@InjectMocks
private AchievementService achievementService;

    @Test
    void shouldFindById() {
        long userId = 1L;
        Achievement achievement = new Achievement();
        Mockito.when(achievementRepository.findById(userId)).thenReturn(Optional.of(achievement));

        Achievement result = achievementService.findById(userId);

        Mockito.verify(achievementRepository, Mockito.times(1)).findById(userId);
        assertEquals(achievement, result);
    }

    @Test
    void shouldThrowException() {
        long userId = 1L;
        Mockito.when(achievementRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> achievementService.findById(userId));
    }
}