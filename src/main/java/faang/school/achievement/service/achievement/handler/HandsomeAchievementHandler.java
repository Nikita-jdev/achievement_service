package faang.school.achievement.service.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.EventProfilePic;
import faang.school.achievement.service.achievement.AchievementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HandsomeAchievementHandler extends EventHandler<EventProfilePic> {
    public HandsomeAchievementHandler(AchievementCache achievementCache,
                                      AchievementService achievementService,
                                      @Value("${achievement.profile_pic.handsome}")
                                      String achievementTitle){
        super(achievementCache, achievementService, achievementTitle);
    }
}
