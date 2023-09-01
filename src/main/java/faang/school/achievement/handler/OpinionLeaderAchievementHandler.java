package faang.school.achievement.handler;

import faang.school.achievement.dto.EventPostDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@EnableAsync
public class OpinionLeaderAchievementHandler extends PostAchievementHandler {

    private final AchievementService service;
    private final String ACHIEVEMENT_TITTLE = "Opinion leader";

    @Async("threadPoolForAchievementHandler")
    @Override
    public void handle(EventPostDto postDto) {
        Achievement achievement = service.getAchievement(ACHIEVEMENT_TITTLE);
        long authorId = getIfOfPostAuthor(postDto);
        long achievementId = achievement.getId();

        if (!service.hasAchievement(authorId, achievementId)) {
            service.checkAndCreateAchievementProgress(authorId, achievementId);
        }

        long progress = service.getProgress(authorId, achievementId);
        if (achievement.getPoints() >= progress) {
            service.giveAchievement(authorId, ACHIEVEMENT_TITTLE);
        }
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
