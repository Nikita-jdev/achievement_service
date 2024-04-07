package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.FollowerEventDto;
import faang.school.achievement.handler.EventHandler;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class FollowerEventListener extends AbstractEventListener<FollowerEventDto> {

    public FollowerEventListener(ObjectMapper objectMapper,
                                 List<EventHandler<FollowerEventDto>> eventHandlers) {
        super(objectMapper, eventHandlers);
    }

    @Async
    @Override
    public void onMessage(@NonNull Message message, byte[] pattern) {
        FollowerEventDto event = deserialize(message, FollowerEventDto.class);
        handle(event.getFolloweeId());
    }

}
