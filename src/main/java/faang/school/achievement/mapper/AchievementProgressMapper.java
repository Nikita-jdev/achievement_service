package faang.school.achievement.mapper;

import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.model.AchievementProgress;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = AchievementMapper.class)
public interface AchievementProgressMapper {

    @Mapping(target = "achievementDto", source = "achievement", qualifiedByName = "toAchievementDto")
    AchievementProgressDto toDto(AchievementProgress achievementProgress);

    default List<AchievementProgressDto> toDtoList(List<AchievementProgress> achievementProgresses) {
        return achievementProgresses.stream()
                .map(this::toDto)
                .toList();
    }

    @Mapping(target = "achievement", source = "achievementDto", qualifiedByName = "toAchievementEntity")
    AchievementProgress toEntity(AchievementProgressDto achievementProgressDto);

    default List<AchievementProgress> toEntityList(List<AchievementProgressDto> achievementProgresses) {
        return achievementProgresses.stream()
                .map(this::toEntity)
                .toList();
    }
}
