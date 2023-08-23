package faang.school.achievement.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "event-handler-thread-pool")
@Data
public class EventHandlerThreadPoolConfig {
    private int queueCapacity;
    private int maxPoolSize;
    private int corePoolSize;
    private String threadNamePrefix;
}
