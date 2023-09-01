package faang.school.achievement.mapper;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.model.Achievement;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AchievementMapper {

    @Named(value = "toAchievementDto")
    AchievementDto toAchievementDto(Achievement achievement);

    @Named("toAchievementEntity")
    Achievement toAchievementEntity (AchievementDto achievementDto);
}
