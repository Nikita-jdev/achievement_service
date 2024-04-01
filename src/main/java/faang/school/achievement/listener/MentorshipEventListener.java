package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.MentorshipStartEvent;
import faang.school.achievement.handler.EventHandler;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MentorshipEventListener extends AbstractEventListener<MentorshipStartEvent> implements MessageListener {
    public MentorshipEventListener(List<EventHandler<MentorshipStartEvent>> eventHandlers, ObjectMapper mapper) {
        super(eventHandlers, mapper);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        handle(message, MentorshipStartEvent.class);
    }

}
