package faang.school.achievement.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.EventProfilePic;
import faang.school.achievement.service.achievement.handler.EventHandler;
import faang.school.achievement.service.achievement.handler.HandsomeAchievementHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ProfilePicEventListener implements MessageListener {
    private final ObjectMapper objectMapper;
    private final EventHandler<EventProfilePic> handsomeAchievementHandler;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            EventProfilePic eventProfilePic = objectMapper.readValue(message.getBody(), EventProfilePic.class);
            handsomeAchievementHandler.handle(eventProfilePic);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
