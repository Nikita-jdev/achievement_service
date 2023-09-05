package faang.school.achievement.filters.filtersAchievement;

import faang.school.achievement.dto.AchievementfilterDto;
import faang.school.achievement.model.Achievement;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

@Component
public class DescriptionAchievementFilter implements AchievementFilter {
    @Override
    public boolean isApplicable(AchievementfilterDto achievementfilterDto) {
        return achievementfilterDto.getDescription() != null;
    }

    @Override
    public List<Achievement> apply(Stream<Achievement> achievementStream, AchievementfilterDto achievementfilterDto) {
        return achievementStream.filter(f -> f.getDescription().contains(achievementfilterDto.getDescription())).toList();
    }
}
