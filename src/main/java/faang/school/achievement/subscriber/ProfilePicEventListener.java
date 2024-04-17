package faang.school.achievement.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.EventProfilePic;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ProfilePicEventListener implements MessageListener {
    private final ObjectMapper objectMapper;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            EventProfilePic userEvent = objectMapper.readValue(message.getBody(), EventProfilePic.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
