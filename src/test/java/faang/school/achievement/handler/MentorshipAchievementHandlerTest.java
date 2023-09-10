package faang.school.achievement.handler;

import faang.school.achievement.dto.MentorshipEventDto;
import faang.school.achievement.service.AchievementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadPoolExecutor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MentorshipAchievementHandlerTest {

    @Mock
    private AchievementService achievementService;

    @Mock
    private ThreadPoolExecutor testThreadPoolExecutor;

    private MentorshipAchievementHandler mentorshipAchievementHandler;

    private MentorshipEventDto mentorshipEventDto;

    @BeforeEach
    void setUp() {
        mentorshipAchievementHandler = new MentorshipAchievementHandler(achievementService, testThreadPoolExecutor);
        String ACHIEVEMENT_NAME = "sensei";
        mentorshipAchievementHandler.setMentorshipAchievementName(ACHIEVEMENT_NAME);
        LocalDateTime currentTime = LocalDateTime.now();
        mentorshipEventDto = MentorshipEventDto.builder()
                .requesterId(2)
                .receiverId(1)
                .createdAt(currentTime)
                .build();
    }

    @Test
    void handleTest() {
        mentorshipAchievementHandler.handle(mentorshipEventDto);

        verify(testThreadPoolExecutor).execute(any(Runnable.class));
    }
}
