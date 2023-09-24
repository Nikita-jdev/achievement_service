package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.redis.CommentEvent;
import faang.school.achievement.handler.EventHandler;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentEventListener extends AbstractEventListener<CommentEvent>  implements MessageListener {
    public CommentEventListener(List<EventHandler<CommentEvent>> commentHandlers, ObjectMapper objectMapper){
        super(commentHandlers, objectMapper);
    }
    @Override
    public void onMessage(Message message, byte[] pattern){
        handleEvent(getMessageBody(message), CommentEvent.class);
    }
}
