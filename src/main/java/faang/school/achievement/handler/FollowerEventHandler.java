package faang.school.achievement.handler;

import faang.school.achievement.dto.FollowerEventDto;
import org.springframework.stereotype.Component;

@Component
public abstract class FollowerEventHandler implements EventHandler<FollowerEventDto>{
    @Override
    public void handle(FollowerEventDto event) {

    }
}
