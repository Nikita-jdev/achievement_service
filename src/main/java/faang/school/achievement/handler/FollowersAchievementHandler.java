package faang.school.achievement.handler;

import faang.school.achievement.dto.FollowerEventDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Setter
@RequiredArgsConstructor
public class FollowersAchievementHandler extends FollowerEventHandler {

    private final AchievementService achievementService;

    @Value("${achievement-service.achievement.followers.name}")
    private String followersAchievementName;

    @Async("followerHandlerThreadPoolExecutor")
    @Override
    public void handle(FollowerEventDto event) {
        Achievement achievement = achievementService.getAchievement(followersAchievementName);
        long userId = event.getFolloweeId();
        long achievementId = achievement.getId();

        if (!achievementService.hasAchievement(achievementId, userId)) {
            achievementService.checkAndCreateAchievementProgress(userId, achievementId);
        }
        long currentProgress = achievementService.getProgress(userId, achievementId);

        if (currentProgress >= achievement.getPoints()) {
            achievementService.giveAchievement(userId, followersAchievementName);
        }
    }
}