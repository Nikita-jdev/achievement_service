package faang.school.achievement.service;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementfilterDto;
import faang.school.achievement.filters.filtersAchievement.AchievementFilter;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AchievementServiceTest {
    @Mock
    private AchievementMapper achievementMapper;
    @Mock
    private AchievementRepository achievementRepository;
    @Mock
    private UserAchievementRepository userAchievementRepository;
    @Mock
    private AchievementProgressRepository achievementProgressRepository;
    @Mock
    private AchievementFilter achievementFilter;
    @Mock
    private List<AchievementFilter> achievementFilters;

    @InjectMocks
    private AchievementService achievementService;

    @BeforeEach
    void getAchievementFilter() {
        List<Achievement> listAch = new ArrayList<>();
        listAch.add(new Achievement());
        listAch.add(new Achievement());
        List<AchievementFilter> achievementFiltersTest = new ArrayList<>();
        achievementFiltersTest.add(achievementFilter);

        when(achievementRepository.findAll()).thenReturn(listAch);

        when(achievementFilters.stream().filter(f -> f.isApplicable(new AchievementfilterDto())))
                .thenReturn(achievementFiltersTest.stream());

        when(achievementFilter.isApplicable(new AchievementfilterDto())).thenReturn(true);

        when(achievementFilter.apply(any(), any())).thenReturn(new ArrayList<>());

        achievementService.getAchievementFilter(new AchievementfilterDto());

        verify(achievementRepository).findAll();
        verify(achievementFilter).isApplicable(any());
        verify(achievementFilter).apply(any(), any());
    }

    @Test
    void getAchievement() {

        when(achievementRepository.findById(any())).thenReturn(Optional.of(new Achievement()));
        when(achievementMapper.toDto(any())).thenReturn(new AchievementDto());

        achievementService.getAchievement(1L);
        verify(achievementRepository).findById(any());
        verify(achievementMapper).toDto(any());
    }

    @Test
    void getAllUserAchievements() {
        List<UserAchievement> listAch = new ArrayList<>();
        listAch.add(new UserAchievement());
        when(userAchievementRepository.findByUserId(1L)).thenReturn(listAch);
        when(achievementMapper.toDto(any())).thenReturn(new AchievementDto());

        achievementService.getAllUserAchievements(1L);

        verify(userAchievementRepository).findByUserId(anyLong());
        verify(achievementMapper).toDto(any());
    }

    @Test
    void getAllAchievementsProgressUsers() {
        List<AchievementProgress> listAchPro = new ArrayList<>();
        when(achievementProgressRepository.findByUserId(1L)).thenReturn(listAchPro);

        achievementService.getAllProgressAchievementsUsers(1L);

        verify(achievementProgressRepository).findByUserId(1L);
    }


}
