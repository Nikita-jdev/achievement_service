package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.AlbumCreatedEventDto;
import faang.school.achievement.handler.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class AlbumEventListener extends AbstractEventListener<AlbumCreatedEventDto> {
    private final List<EventHandler<AlbumCreatedEventDto>> albumHandlers;

    public AlbumEventListener(ObjectMapper objectMapper, List<EventHandler<AlbumCreatedEventDto>> albumHandlers) {
        super(objectMapper);
        this.albumHandlers = albumHandlers;
    }


    @Override
    public void onMessage(Message message, byte[] pattern) {
        AlbumCreatedEventDto albumCreatedEventDto = getEvent(message, AlbumCreatedEventDto.class);
        albumHandlers.forEach(handler -> handler.handle(albumCreatedEventDto.getAuthorId()));
    }
}