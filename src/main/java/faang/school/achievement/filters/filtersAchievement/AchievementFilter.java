package faang.school.achievement.filters.filtersAchievement;

import faang.school.achievement.dto.AchievementfilterDto;
import faang.school.achievement.model.Achievement;

import java.util.List;
import java.util.stream.Stream;

public interface AchievementFilter {
    boolean isApplicable(AchievementfilterDto achievementfilterDto);
    List<Achievement> apply(Stream<Achievement> achievementStream,AchievementfilterDto achievementfilterDto);

}
