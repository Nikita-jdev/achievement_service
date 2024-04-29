package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.event.SkillAcquiredEvent;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class WhoeverAchievementHandler implements EventHandler<SkillAcquiredEvent> {
    private final AchievementCache achievementCache;
    private final AchievementService achievementService;
    @Value("${achievement.skill.name}")
    private String achievementName;

    @Override
    @Transactional
    @Async("executorService")
    public void handle(SkillAcquiredEvent event) {
        Achievement achievement = achievementCache.get(achievementName);
        achievementService.achievementProcess(event.getReceiverId(), achievement);
    }
}
