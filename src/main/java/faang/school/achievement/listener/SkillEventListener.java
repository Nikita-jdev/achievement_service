package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.SkillAcquiredEventDto;
import faang.school.achievement.handler.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SkillEventListener extends AbstractEventListener<SkillAcquiredEventDto> {
    private final List<EventHandler<SkillAcquiredEventDto>> eventHandlers;

    @Autowired
    public SkillEventListener(ObjectMapper objectMapper, List<EventHandler<SkillAcquiredEventDto>> eventHandlers) {
        super(objectMapper);
        this.eventHandlers = eventHandlers;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        SkillAcquiredEventDto event = convertJsonToString(message, SkillAcquiredEventDto.class);
        eventHandlers.forEach(handler -> handler.handle(event.getReceiverId()));
    }
}
