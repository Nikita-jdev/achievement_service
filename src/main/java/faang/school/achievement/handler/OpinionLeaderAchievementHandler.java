package faang.school.achievement.handler;

import faang.school.achievement.dto.EventPostDto;
import faang.school.achievement.service.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class OpinionLeaderAchievementHandler extends AbstractAchievementHandler<EventPostDto> {

    private final ThreadPoolTaskExecutor postEventThreadPoolExecutor;
    private final String ACHIEVEMENT_TITTLE = "Opinion leader";

    @Autowired
    public OpinionLeaderAchievementHandler(AchievementService achievementService, ThreadPoolTaskExecutor postEventThreadPoolExecutor) {
        super(achievementService);
        this.postEventThreadPoolExecutor = postEventThreadPoolExecutor;
    }

    @Override
    public void handle(EventPostDto postDto) {
        postEventThreadPoolExecutor.execute(() -> {
            long userId = getIfOfPostAuthor(postDto);
            handleAchievement(userId, ACHIEVEMENT_TITTLE);
        });
    }

    private long getIfOfPostAuthor(EventPostDto postDto) {
        long postAuthorId;
        if (postDto.getAuthorId() == null) {
            postAuthorId = postDto.getProjectId();
        } else {
            postAuthorId = postDto.getAuthorId();
        }
        return postAuthorId;
    }
}