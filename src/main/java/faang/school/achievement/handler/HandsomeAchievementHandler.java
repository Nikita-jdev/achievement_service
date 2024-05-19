package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.event.EventProfilePic;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class HandsomeAchievementHandler implements EventHandler<EventProfilePic>{
    private final AchievementService achievementService;
    private final AchievementCache achievementCache;
    @Value("${achievement.profile_pic.handsome}")
    private String achievementTitle;
    @Override
    public void handle(EventProfilePic event) {
        Achievement achievement = achievementCache.get(achievementTitle);
        achievementService.workWithAchievement(event.getUserId(), achievementTitle, achievement);
    }
}
