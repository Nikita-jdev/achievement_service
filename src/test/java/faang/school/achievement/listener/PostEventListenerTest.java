package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.PostEventDto;
import faang.school.achievement.handler.EventHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.connection.Message;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import faang.school.achievement.exception.MessageReadException;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PostEventListenerTest {
    @InjectMocks
    private PostEventListener listener;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private List<EventHandler<PostEventDto>> handlers;
    private PostEventDto dto;
    private Message message;

    @BeforeEach
    void setUp() throws IOException {
        dto = new PostEventDto();
        dto.setAuthorId(1L);
        dto.setPostId(1L);

        message = mock(Message.class);

        when(objectMapper.readValue(any(byte[].class), eq(PostEventDto.class))).thenReturn(dto);
    }

    @Test
    void testOnMessage() throws IOException {
        byte[] jsonBytes = new byte[]{};
        when(message.getBody()).thenReturn(jsonBytes);

        listener.onMessage(message, null);

        verify(objectMapper).readValue(jsonBytes, PostEventDto.class);
    }

    @Test
    void testOnMessageWithIOException() throws IOException {
        IOException ioException = new IOException("Test IO Exception");

        when(objectMapper.readValue(any(byte[].class), eq(PostEventDto.class))).thenThrow(ioException);

        byte[] jsonBytes = new byte[]{};
        when(message.getBody()).thenReturn(jsonBytes);

        assertThrows(MessageReadException.class, () -> listener.onMessage(message, null));
        verify(objectMapper).readValue(jsonBytes, PostEventDto.class);
        verifyNoInteractions(handlers);
    }
}