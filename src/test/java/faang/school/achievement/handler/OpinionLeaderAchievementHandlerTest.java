package faang.school.achievement.handler;

import faang.school.achievement.dto.EventPostDto;
import faang.school.achievement.service.AchievementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OpinionLeaderAchievementHandlerTest {

    @Mock
    private AchievementService achievementService;
    @Mock
    private ThreadPoolTaskExecutor testThreadPoolTaskExecutor;

    @InjectMocks
    private OpinionLeaderAchievementHandler achievementHandler;

    private final long AUTHOR_ID = 1L;
    private EventPostDto postDto;

    @BeforeEach
    void initData() {
        postDto = EventPostDto.builder()
                .authorId(AUTHOR_ID)
                .postId(1L)
                .build();
    }

    @Test
    void testHandle() {
        achievementHandler.handle(postDto);

        verify(testThreadPoolTaskExecutor).execute(any(Runnable.class));
    }
}
