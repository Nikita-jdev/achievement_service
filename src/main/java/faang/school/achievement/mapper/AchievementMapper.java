package faang.school.achievement.mapper;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.model.Achievement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AchievementMapper {
    @Mapping(target = "userAchievementIds", ignore = true)
    @Mapping(target = "progressIds", ignore = true)
    AchievementDto toDto (Achievement achievement);
}