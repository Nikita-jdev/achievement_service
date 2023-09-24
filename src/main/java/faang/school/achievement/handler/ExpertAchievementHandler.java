package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.redis.CommentEvent;
import faang.school.achievement.service.AchievementService;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class ExpertAchievementHandler extends AbstractEventHandler implements EventHandler<CommentEvent> {
    public ExpertAchievementHandler(AchievementCache achievementCache, AchievementService achievementService, AsyncTaskExecutor asyncTaskExecutor) {
        super(achievementCache, achievementService, asyncTaskExecutor);
    }

    @Override
    public void handle(CommentEvent commentEvent) {
        handleAsync(commentEvent.getAuthorId(), "Expert");
    }
}
