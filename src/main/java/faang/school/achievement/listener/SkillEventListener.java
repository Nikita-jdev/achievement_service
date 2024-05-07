package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.SkillAcquiredEvent;
import faang.school.achievement.handler.WhoeverAchievementHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SkillEventListener implements MessageListener {
    private final ObjectMapper objectMapper;
    private final WhoeverAchievementHandler whoeverAchievementHandler;
    @Override
    public void onMessage(Message message, byte[] pattern){
        try{
            SkillAcquiredEvent skillAcquiredEvent = objectMapper.readValue(message.getBody(), SkillAcquiredEvent.class);
            log.info("Объект skillAcquiredEvent получен");
            whoeverAchievementHandler.handle(skillAcquiredEvent);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
