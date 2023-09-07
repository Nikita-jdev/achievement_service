package faang.school.achievement.listener;

import faang.school.achievement.dto.FollowerEventDto;
import faang.school.achievement.handler.AbstractAchievementHandler;
import faang.school.achievement.handler.FollowersAchievementHandler;
import faang.school.achievement.mapper.JsonObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.connection.Message;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FollowerEventListenerTest {

    @Mock
    private JsonObjectMapper jsonObjectMapper;

    @Mock
    private FollowersAchievementHandler followerHandler;
    @Mock
    private Message message;

    private FollowerEventListener followerEventListener;

    @BeforeEach
    void setUp() {
        followerHandler.setFollowersAchievementName("subscribers");
        List<AbstractAchievementHandler<FollowerEventDto>> eventHandlerList = List.of(followerHandler);
        followerEventListener = new FollowerEventListener(jsonObjectMapper, eventHandlerList);
    }

    @Test
    void onMessageTest() {
        String chanelName = "channel_topic";
        byte[] chanelNameBytes = chanelName.getBytes();

        String json = "{\"followerId\":2,\"followeeId\":1,\"subscriptionTime\":\"2023-08-20T12:34:56.789\"}";
        byte[] bodyBytes = json.getBytes();

        LocalDateTime currentTime = LocalDateTime.now();

        FollowerEventDto followerEventDto = FollowerEventDto.builder()
                .followerId(2)
                .followeeId(1)
                .subscriptionTime(currentTime)
                .build();

        when(message.getChannel()).thenReturn(chanelNameBytes);
        when(message.getBody()).thenReturn(bodyBytes);
        when(jsonObjectMapper.readValue(bodyBytes, FollowerEventDto.class)).thenReturn(followerEventDto);

        followerEventListener.onMessage(message, new byte[0]);

        verify(jsonObjectMapper).readValue(bodyBytes, FollowerEventDto.class);
        verify(followerHandler).handle(followerEventDto);
    }
}