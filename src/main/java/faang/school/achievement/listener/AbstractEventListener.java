package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractEventListener<T> implements MessageListener {
    protected final ObjectMapper objectMapper;
    private final Class<T> type;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            T event = objectMapper.readValue(message.getBody(), type);
            log.info("Получили event {}", event);
            process(event);
            log.info("Выполнили обработку процесса");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract void process(T event);
}