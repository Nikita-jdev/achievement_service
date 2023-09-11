package faang.school.achievement.handler;

import faang.school.achievement.dto.EventDto;
import faang.school.achievement.exception.AchievementNotFoundException;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public abstract class AbstractGoalEventHandler<T extends EventDto> implements EventHandler<T> {
    protected final AchievementService achievementService;

    protected Achievement getByTitle(String title) {
        Achievement achievement = achievementService.getAchievementByTitle(title);
        if (achievement == null) {
            throw new AchievementNotFoundException("Achievement '" + title + "' not found");
        }
        return achievement;
    }

    protected AchievementProgress getAchievementProgress(Long userId, Achievement achievement) {
        Optional<AchievementProgress> progress =
                achievementService.getAchievementProgress(userId, achievement.getId());

        if (progress.isEmpty()) {
            AchievementProgress achievementProgress = AchievementProgress.builder()
                    .achievement(achievement)
                    .userId(userId)
                    .currentPoints(0)
                    .version(1)
                    .build();

            return achievementService.createAchievementProgressIfNecessary(achievementProgress);
        } else {
            return progress.get();
        }
    }

    protected void giveAchievementIfScoredPoints(AchievementProgress achievementProgress, Achievement achievement){
        if (achievementProgress.getCurrentPoints() >= achievement.getPoints()) {
            UserAchievement userAchievement = UserAchievement.builder()
                    .achievement(achievement)
                    .userId(achievementProgress.getUserId())
                    .build();

            achievementService.giveAchievement(userAchievement);
        }
    }

}

