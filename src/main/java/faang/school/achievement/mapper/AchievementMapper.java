package faang.school.achievement.mapper;


import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.model.Achievement;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AchievementMapper {
    Achievement toEntity(AchievementDto achievementDto);
    AchievementDto toDto(Achievement achievement);

}
