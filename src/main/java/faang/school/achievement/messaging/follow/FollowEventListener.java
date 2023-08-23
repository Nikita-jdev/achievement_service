package faang.school.achievement.messaging.follow;

import faang.school.achievement.config.AsyncConfig;
import faang.school.achievement.dto.follow.FollowEventDto;
import faang.school.achievement.service.handler.EventHandler;
import faang.school.achievement.util.JsonMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Executor;

@Component
@RequiredArgsConstructor
@Slf4j
public class FollowEventListener implements MessageListener {
    private final JsonMapper jsonMapper;
    private final List<EventHandler<FollowEventDto>> handlers;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.info("FollowEventListener has received a message: " + message.toString());

        try {
            var followEventDto = jsonMapper.toObject(message.toString(), FollowEventDto.class);
            handlers.forEach(handler -> handler.handle(followEventDto));
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("Error with parsing in FollowEventListener: " + e.getMessage());
        }
    }
}
