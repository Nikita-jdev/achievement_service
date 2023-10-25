package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.RecommendationEventDto;
import faang.school.achievement.handler.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecommendationEventListener extends AbstractEventListener<RecommendationEventDto> {
    private final List<EventHandler<RecommendationEventDto>> eventHandlers;

    @Autowired
    public RecommendationEventListener(ObjectMapper objectMapper, List<EventHandler<RecommendationEventDto>> eventHandlers) {
        super(objectMapper);
        this.eventHandlers = eventHandlers;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        RecommendationEventDto event = convertJsonToString(message, RecommendationEventDto.class);
        eventHandlers.forEach(handler -> handler.handle(event.getAuthorId()));
    }
}
