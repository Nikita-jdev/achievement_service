package faang.school.achievement.handler;

import faang.school.achievement.dto.MentorshipEventDto;
import faang.school.achievement.service.AchievementService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;

@Component
@Setter
public class MentorshipAchievementHandler extends AbstractAchievementHandler<MentorshipEventDto> {

    private final ThreadPoolExecutor mentorshipEventThreadPoolExecutor;
    @Value("${achievement-service.achievement.mentorship.name}")
    private String mentorshipAchievementName;

    @Autowired
    public MentorshipAchievementHandler(AchievementService achievementService, ThreadPoolExecutor mentorshipEventThreadPoolExecutor) {
        super(achievementService);
        this.mentorshipEventThreadPoolExecutor = mentorshipEventThreadPoolExecutor;
    }

    @Override
    public void handle(MentorshipEventDto event) {
        mentorshipEventThreadPoolExecutor.execute(() -> {
            long userId = event.getRequesterId();
            handleAchievement(userId, mentorshipAchievementName);
        });
    }
}
