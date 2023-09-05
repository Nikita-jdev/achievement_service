package faang.school.achievement.filters.filtersAchievement;

import faang.school.achievement.dto.AchievementfilterDto;
import faang.school.achievement.model.Achievement;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

@Component
public class RarityAchievementFilter implements AchievementFilter {
    @Override
    public boolean isApplicable(AchievementfilterDto achievementfilterDto) {
        return achievementfilterDto.getRarity() != null;
    }

    @Override
    public List<Achievement> apply(Stream<Achievement> achievementStream, AchievementfilterDto achievementfilterDto) {
        return achievementStream.filter(f -> f.getRarity().equals(achievementfilterDto.getRarity())).toList();
    }
}
