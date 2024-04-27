package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.SkillAcquiredEvent;
import faang.school.achievement.handler.EventHandler;
import faang.school.achievement.handler.WhoeverAchievementHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SkillEventListener implements MessageListener {
    private final ObjectMapper objectMapper;
    @Override
    public void onMessage(Message message, byte[] pattern){
        try{
            SkillAcquiredEvent skillAcquiredEvent = objectMapper.readValue(message.getBody(), SkillAcquiredEvent.class);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
