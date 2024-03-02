package faang.school.achievement.dto;

import lombok.Data;

@Data
public class AlbumCreatedEventDto {
    private long id;
    private long authorId;
    private String title;
}