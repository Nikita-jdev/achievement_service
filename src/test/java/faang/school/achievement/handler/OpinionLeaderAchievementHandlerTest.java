/*package faang.school.achievement.handler;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.service.AchievementService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OpinionLeaderAchievementHandlerTest {
    @InjectMocks
    private OpinionLeaderAchievementHandler opinionLeaderAchievementHandler;
    @Mock
    private AchievementService achievementService;
    @Mock
    private AchievementCache achievementCache;
    @Mock
    private AbstractEventHandler abstractEventHandler;

    @Test
    void testHandleRunSuccessful() {
        opinionLeaderAchievementHandler.handle(1L);
        verify(abstractEventHandler, times(1)).abstractHandle(1L, anyString());
    }


}
*/