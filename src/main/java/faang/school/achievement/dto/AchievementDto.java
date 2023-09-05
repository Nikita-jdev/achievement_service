package faang.school.achievement.dto;

import faang.school.achievement.model.Rarity;
import lombok.Data;

@Data
public class AchievementDto {
    private Long id;
    private String title;
    private String description;
    private long points;
    private Rarity rarity;

}
