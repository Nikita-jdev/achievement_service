package faang.school.achievement.controller;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementfilterDto;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/achievement")
public class AchievementController {
    private final AchievementService achievementService;


    @GetMapping("{id}")
    public AchievementDto getAchievement(@PathVariable Long id) {
        return achievementService.getAchievement(id);
    }

    @GetMapping("/user/{id}")
    public List<AchievementDto> getAllUserAchievements(@PathVariable Long id) {
        return achievementService.getAllUserAchievements(id);
    }

    @GetMapping("/progress/{id}")
    public Map<AchievementDto, Long> getAllUserAchievementsProgress(@PathVariable Long id) {
        return achievementService.getAllProgressAchievementsUsers(id);
    }

    @PostMapping("/filters")
    public List<AchievementDto> getAchievementFilter(@RequestBody AchievementfilterDto achievementfilterDto) {
        return achievementService.getAchievementFilter(achievementfilterDto);
    }

}
