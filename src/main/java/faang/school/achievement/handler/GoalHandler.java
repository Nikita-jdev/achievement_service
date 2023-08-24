package faang.school.achievement.handler;

import faang.school.achievement.dto.GoalSetEventDto;
import org.springframework.stereotype.Component;

@Component
public abstract class GoalHandler implements EventHandler<GoalSetEventDto>{
    public abstract void handle(GoalSetEventDto goalSetEventDto);
}

