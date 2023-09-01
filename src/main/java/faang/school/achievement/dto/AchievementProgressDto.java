package faang.school.achievement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AchievementProgressDto {
    private long id;
    private AchievementDto achievementDto;
    private long userId;
    private long currentPoints;
}