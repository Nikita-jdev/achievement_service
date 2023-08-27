package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.GoalSetEventDto;
import faang.school.achievement.handler.EventHandler;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GoalSetListener extends AbstractEventListener<GoalSetEventDto> implements MessageListener {

    public GoalSetListener(List<EventHandler<GoalSetEventDto>> eventHandlers, ObjectMapper objectMapper) {
        super(eventHandlers, objectMapper);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        handleEvent(getMessage(message), GoalSetEventDto.class);
    }
}
