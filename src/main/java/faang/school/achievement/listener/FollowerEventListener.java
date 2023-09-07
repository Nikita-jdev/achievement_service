package faang.school.achievement.listener;

import faang.school.achievement.dto.FollowerEventDto;
import faang.school.achievement.handler.AbstractAchievementHandler;
import faang.school.achievement.mapper.JsonObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class FollowerEventListener implements MessageListener {

    private final JsonObjectMapper jsonObjectMapper;
    private final List<AbstractAchievementHandler<FollowerEventDto>> list;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.info("Event has been received from {} topic", new String(message.getChannel(), StandardCharsets.UTF_8));

        FollowerEventDto dto = jsonObjectMapper.readValue(message.getBody(), FollowerEventDto.class);
        list.forEach(handler -> handler.handle(dto));
    }
}
