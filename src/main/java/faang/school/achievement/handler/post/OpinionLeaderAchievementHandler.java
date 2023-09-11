package faang.school.achievement.handler.post;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.PostEventDto;
import faang.school.achievement.handler.AbstractAchievementHandler;
import faang.school.achievement.service.AchievementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OpinionLeaderAchievementHandler extends AbstractAchievementHandler<PostEventDto>  {
    @Value("${spring.achievements.post.title}")
    private String achievementTitle;

    public OpinionLeaderAchievementHandler(AchievementCache achievementCache,
                                           AchievementService achievementService) {
        super(achievementCache, achievementService);
    }

    @Override
    public void handle(PostEventDto event) {
        handleAchievement(achievementTitle, event.getAuthorId());
    }

    @Override
    public String getAchievementTitle() {
        return achievementTitle;
    }
}