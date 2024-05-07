package faang.school.achievement.handler;

import faang.school.achievement.event.GoalSetEvent;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class CollectorAchievementHandler implements EventHandler<GoalSetEvent> {

    private final AchievementService achievementService;
    @Value("${achievement.collector}")
    private String achievementTitle;

    @Override
    @Transactional
    public void handle(GoalSetEvent goalSetEvent) {
        long userId = goalSetEvent.getUserId();
        achievementService.workWithAchievement(userId, achievementTitle);
    }
}
