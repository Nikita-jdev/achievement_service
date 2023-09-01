package faang.school.achievement.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.AchievementEventDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AchievementPublisherTest {

    @Mock
    private ChannelTopic achievementTopicName;
    @Mock
    private RedisTemplate<String, Object> redisPublisherTemplate;
    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private AchievementPublisher achievementPublisher;

    @Test
    void publishTest() throws JsonProcessingException {
        LocalDateTime currentTime = LocalDateTime.now();

        AchievementEventDto achievementEventDto = AchievementEventDto.builder()
                .achievementId(1)
                .receiverId(1)
                .achievementTitle("subscribers")
                .achievedAt(currentTime)
                .build();

        String json = "{\"achievementId\":123,\"receiverId\":456,\"achievedAt\":\"2023-08-20T12:34:56.789\"}";

        when(objectMapper.writeValueAsString(achievementEventDto)).thenReturn(json);
        when(achievementTopicName.getTopic()).thenReturn("achievement_channel");

        achievementPublisher.publish(achievementEventDto);

        verify(redisPublisherTemplate).convertAndSend("achievement_channel", json);
    }

}