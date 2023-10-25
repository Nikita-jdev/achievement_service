package faang.school.achievement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class SkillAcquiredEventDto {
    private long skillId;
    private long receiverId;
    private List<UserSkillGuaranteeDto> guarantees;
}
