package faang.school.achievement.service;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementfilterDto;
import faang.school.achievement.exceptions.AchievementException;
import faang.school.achievement.filters.filtersAchievement.AchievementFilter;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class AchievementService {
    private final AchievementRepository achievementRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final AchievementProgressRepository achievementProgressRepository;
    private final AchievementMapper achievementMapper;
    private final List<AchievementFilter> achievementFilters;


    public AchievementDto getAchievement(Long id) {
        if (id == 0 || id == null) {
            throw new AchievementException("id = null");
        }
        return achievementMapper.toDto(achievementRepository.findById(id).get());
    }

    public List<AchievementDto> getAllUserAchievements(Long id) {
        List<Achievement> achievementList = new ArrayList<>();
        List<AchievementDto> achievementDtoList = new ArrayList<>();
        if (id == 0 || id == null) {
            throw new AchievementException("id = null");
        }
        userAchievementRepository.findByUserId(id)
                .stream()
                .forEach(ach -> achievementList.add(ach.getAchievement()));
        achievementList.stream()
                .forEach(achievement -> {
                    achievementDtoList.add(achievementMapper.toDto(achievement));
                });
        return achievementDtoList;
    }

    public Map<AchievementDto, Long> getAllProgressAchievementsUsers(Long id) {
        Map<AchievementDto, Long> achievementProgressMap = new HashMap<>();
        if (id == 0 || id == null) {
            throw new AchievementException("id = null");
        }
        achievementProgressRepository.findByUserId(id)
                .stream()
                .forEach(achievementProgress -> {
                    AchievementDto achDto = achievementMapper.toDto(achievementProgress.getAchievement());
                    achievementProgressMap.put(achDto, achievementProgress.getCurrentPoints());
                });
        return achievementProgressMap;
    }

    public List<AchievementDto> getAchievementFilter(AchievementfilterDto filters) {
        List<Achievement> invitations = (List<Achievement>) achievementRepository.findAll();
        if (invitations.isEmpty()) {
            return new ArrayList<>();
        }
        return applyFilter(invitations.stream(), filters);
    }


    private List<AchievementDto> applyFilter(Stream<Achievement> achievementStream, AchievementfilterDto filterDto) {
        List<Achievement> invitations = new ArrayList<>();
        List<AchievementFilter> requiredFilters = achievementFilters.stream()
                .filter(filter -> filter.isApplicable(filterDto))
                .toList();
        for (AchievementFilter filter : requiredFilters) {
            invitations = filter.apply(achievementStream, filterDto);
        }
        return invitations.stream().map(achievementMapper::toDto).toList();
    }

}



