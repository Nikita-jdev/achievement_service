package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.RecommendationEventDto;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class RecommendationListener extends AbstractEventListener<RecommendationEventDto> implements MessageListener {

    @Autowired
    public RecommendationListener(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public void onMessage(@NotNull Message message, byte[] pattern) {

    }
}
