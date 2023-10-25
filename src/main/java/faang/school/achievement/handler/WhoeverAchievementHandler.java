package faang.school.achievement.handler;

import faang.school.achievement.dto.SkillAcquiredEventDto;
import faang.school.achievement.service.AchievementProgressService;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.UserAchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WhoeverAchievementHandler extends AbstractHandler<SkillAcquiredEventDto>{
    @Autowired
    public WhoeverAchievementHandler(AchievementService achievementService,
                                     UserAchievementService userAchievementService,
                                     AchievementProgressService achievementProgressService,
                                     @Value("${skiller.title}") String title) {
        super(achievementService, userAchievementService, achievementProgressService, title);
    }
}
