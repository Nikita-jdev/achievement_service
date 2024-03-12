package faang.school.achievement.listener;

import faang.school.achievement.dto.MentorshipStartEvent;
import faang.school.achievement.handler.SenseyAchievementHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class MentorshipEventListenerTest {

    @Mock
    private SenseyAchievementHandler senseyAchievementHandler;
    @InjectMocks
    private MentorshipEventListener mentorshipEventListener;


    @Test
    void processEvent() {
        mentorshipEventListener = new MentorshipEventListener(List.of(senseyAchievementHandler));
        MentorshipStartEvent event = new MentorshipStartEvent();
        event.setMenteeId(1L);
        mentorshipEventListener.processEvent(event);
        Mockito.verify(senseyAchievementHandler, Mockito.times(1)).handle(event.getMenteeId());
    }
}