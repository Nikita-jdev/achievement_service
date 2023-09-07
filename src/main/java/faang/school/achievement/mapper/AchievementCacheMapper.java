package faang.school.achievement.mapper;

import faang.school.achievement.dto.AchievementCacheDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AchievementCacheMapper {

    @Mapping(target = "userAchievements", qualifiedByName = "toDtoAchievement")
    @Mapping(target = "progresses", qualifiedByName = "toDtoProgress")
    AchievementCacheDto toDto(Achievement achievement);

    @Mapping(target = "userAchievements", qualifiedByName = "toEntityAchievement")
    @Mapping(target = "progresses", qualifiedByName = "toEntityProgress")
    Achievement toEntity(AchievementCacheDto achievementCacheDto);

    @Named("toDtoAchievement")
    default List<Long> toDtoAchievement(List<UserAchievement> userAchievements) {
        return userAchievements.stream()
                .map(UserAchievement::getId)
                .toList();
    }

    @Named("toDtoProgress")
    default List<Long> toDtoProgress(List<AchievementProgress> achievementProgresses) {
        return achievementProgresses.stream()
                .map(AchievementProgress::getId)
                .toList();
    }

    @Named("toEntityAchievement")
    default List<UserAchievement> toEntityAchievement(List<Long> userAchievements) {
        return userAchievements.stream()
                .map(value -> UserAchievement.builder().id(value).build())
                .toList();
    }

    @Named("toEntityProgress")
    default List<AchievementProgress> toEntityProgress(List<Long> achievementProgresses) {
        return achievementProgresses.stream()
                .map(value -> AchievementProgress.builder().id(value).build())
                .toList();
    }
}
