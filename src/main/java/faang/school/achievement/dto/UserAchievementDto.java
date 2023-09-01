package faang.school.achievement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAchievementDto {
    private long id;
    private AchievementDto achievementDto;
    private long userId;
    private LocalDateTime receivedAt;
}