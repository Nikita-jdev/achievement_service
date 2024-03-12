package faang.school.achievement.listener;

import faang.school.achievement.dto.MentorshipStartEvent;
import faang.school.achievement.handler.EventHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MentorshipEventListener extends AbstractEventListener<MentorshipStartEvent> {


    public MentorshipEventListener(List<EventHandler<MentorshipStartEvent>> eventHandlers) {
        super(MentorshipStartEvent.class, eventHandlers);
    }

    @Override
    protected void processEvent(MentorshipStartEvent event) {
        eventHandlers.forEach(handler -> handler.handle(event.getMenteeId()));
    }
}
