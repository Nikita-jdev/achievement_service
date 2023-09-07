package faang.school.achievement.mapper;

import faang.school.achievement.dto.UserAchievementDto;
import faang.school.achievement.model.UserAchievement;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = AchievementMapper.class)
public interface UserAchievementMapper {

    @Mapping(target = "achievementDto", source = "achievement", qualifiedByName = "toAchievementDto")
    @Mapping(target = "receivedAt", source = "createdAt")
    UserAchievementDto toDto(UserAchievement userAchievement);

    default List<UserAchievementDto> toDtoList(List<UserAchievement> userAchievements) {
        return userAchievements.stream()
                .map(this::toDto)
                .toList();
    }

    @Mapping(target = "achievement", source = "achievementDto", qualifiedByName = "toAchievementEntity")
    @Mapping(target = "createdAt", source = "receivedAt")
    UserAchievement toEntity(UserAchievementDto userAchievementDto);

    default List<UserAchievement> toEntityList(List<UserAchievementDto> userAchievements) {
        return userAchievements.stream()
                .map(this::toEntity)
                .toList();
    }
}