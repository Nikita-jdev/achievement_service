package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.GoalSetEventDto;
import faang.school.achievement.handler.AbstractGoalEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GoalEventListener extends AbstractEventListener<GoalSetEventDto> implements MessageListener {
    private final List<AbstractGoalEventHandler<GoalSetEventDto>> goalAchievementHandlers;

    @Autowired
    public GoalEventListener(ObjectMapper objectMapper,
                             List<AbstractGoalEventHandler<GoalSetEventDto>> goalAchievementHandlers) {
        super(objectMapper);
        this.goalAchievementHandlers = goalAchievementHandlers;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        GoalSetEventDto goalSetEventDto = convertJsonToString(message, GoalSetEventDto.class);
        goalAchievementHandlers.forEach(eventHandler -> eventHandler.handle(goalSetEventDto));
    }
}
