package faang.school.achievement.messaging.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.TeamEventDto;
import faang.school.achievement.handler.ManagerAchievementHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class TeamEventListener implements MessageListener {

    private final ObjectMapper objectMapper;
    private final ManagerAchievementHandler managerAchievementHandler;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            TeamEventDto teamEventDto = objectMapper.readValue(message.getBody(), TeamEventDto.class);
            log.info("Получено событие о создании команды c ID {}, создатель с ID: {}, на проекте с ID: {}",
                    teamEventDto.getTeamId(),
                    teamEventDto.getUserId(),
                    teamEventDto.getProjectId()
            );
            managerAchievementHandler.handle(teamEventDto.getUserId());
        } catch (IOException e) {
            log.error("Не удалось преобразовать сообщение ", e);
            throw new RuntimeException(e);
        }
    }
}