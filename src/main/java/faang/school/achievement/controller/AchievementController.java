package faang.school.achievement.controller;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.UserAchievementDto;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/achievements")
@Slf4j
public class AchievementController {

    private final AchievementService achievementService;

    @GetMapping("/achievement/{title}")
    public AchievementDto getAchievementByTitle(@PathVariable String title) {
        return achievementService.getAchievementByTitle(title);
    }

    @GetMapping
    public Page<AchievementDto> getAllAchievements(@PageableDefault(size = 20) Pageable pageable) {
        return achievementService.getAllAchievements(pageable);
    }

    @GetMapping("/user/{userId}")
    public Page<UserAchievementDto> getUserAchievements(@PathVariable long userId) {
        return achievementService.getUserAchievements(userId);
    }
}
