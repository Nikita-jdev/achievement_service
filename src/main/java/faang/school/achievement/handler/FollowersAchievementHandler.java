package faang.school.achievement.handler;

import faang.school.achievement.dto.FollowerEventDto;
import faang.school.achievement.service.AchievementService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;

@Component
@Setter
public class FollowersAchievementHandler extends AbstractAchievementHandler<FollowerEventDto> {

    private final ThreadPoolExecutor followerEventThreadPoolExecutor;
    @Value("${achievement-service.achievement.followers.name}")
    private String followersAchievementName;

    @Autowired
    public FollowersAchievementHandler(AchievementService achievementService, ThreadPoolExecutor followerEventThreadPoolExecutor) {
        super(achievementService);
        this.followerEventThreadPoolExecutor = followerEventThreadPoolExecutor;
    }

    @Override
    public void handle(FollowerEventDto event) {
        followerEventThreadPoolExecutor.execute(() -> {
            long userId = event.getFolloweeId();
            handleAchievement(userId, followersAchievementName);
        });
    }
}