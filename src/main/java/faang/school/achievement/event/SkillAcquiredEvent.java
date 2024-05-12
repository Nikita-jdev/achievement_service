package faang.school.achievement.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkillAcquiredEvent {
    private Long skillId;
    private Long authorId;
    private Long receiverId;
}
