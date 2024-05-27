package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import faang.school.achievement.dto.FollowerEvent;
import faang.school.achievement.handler.EventHandler;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class FollowerEventListener extends AbstractListener<FollowerEvent> {
    public FollowerEventListener(ObjectMapper objectMapper,
                                 List<EventHandler<FollowerEvent>> eventHandlers){
        super(objectMapper.registerModule(new JavaTimeModule()), eventHandlers);
    }
    @Override
    protected FollowerEvent listenEvent(Message message) throws IOException {
        return objectMapper.readValue(message.getBody(), FollowerEvent.class);
    }
}
