package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.EventProfilePic;
import faang.school.achievement.handler.EventHandler;
import org.springframework.stereotype.Component;

@Component
public class ProfilePicEventListener extends AbstractEventListener<EventProfilePic> {

    private final EventHandler<EventProfilePic> handsomeAchievementHandler;

    public ProfilePicEventListener(ObjectMapper objectMapper,
                                   EventHandler<EventProfilePic> handsomeAchievementHandler) {
        super(objectMapper, EventProfilePic.class);
        this.handsomeAchievementHandler = handsomeAchievementHandler;
    }

    @Override
    public void process(EventProfilePic event) {
        handsomeAchievementHandler.handle(event);
    }
}
