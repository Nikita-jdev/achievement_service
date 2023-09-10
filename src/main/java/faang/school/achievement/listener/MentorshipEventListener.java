package faang.school.achievement.listener;

import faang.school.achievement.dto.MentorshipEventDto;
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
public class MentorshipEventListener implements MessageListener {

    private final JsonObjectMapper jsonObjectMapper;
    private final List<AbstractAchievementHandler<MentorshipEventDto>> list;
    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.info("Event has been received from {} topic", new String(message.getChannel(), StandardCharsets.UTF_8));
        MentorshipEventDto dto = jsonObjectMapper.readValue(message.getBody(), MentorshipEventDto.class);
        list.forEach(h -> h.handle(dto));
    }
}
