package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.PostEventDto;
import faang.school.achievement.handler.EventHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class PostEventListener extends AbstractListener<PostEventDto> {
    @Value("${spring.achievements.post.title}")
    private String achievementTitle;

    public PostEventListener(ObjectMapper objectMapper, List<EventHandler<PostEventDto>> eventHandlers) {
        super(objectMapper, eventHandlers);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        PostEventDto event = readValue(message.getBody(), PostEventDto.class);
        handleEvent(event, achievementTitle);
    }
}