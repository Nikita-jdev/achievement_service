package faang.school.achievement.dto.follow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FollowEventDto {
    private Long subscriberId;
    private Long targetUserId;
    private Long projectId;
    private LocalDateTime subscriptionDateTime;
}