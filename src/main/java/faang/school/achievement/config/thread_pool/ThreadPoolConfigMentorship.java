package faang.school.achievement.config.thread_pool;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadPoolConfigMentorship {

    @Value("${achievement-service.mentorship-handler.thread-pool.corePoolSize}")
    private int corePoolSize;
    @Value("${achievement-service.mentorship-handler.thread-pool.maximumPoolSize}")
    private int maximumPoolSize;
    @Value("${achievement-service.mentorship-handler.thread-pool.keepAliveTime}")
    private int keepAliveTime;

    @Bean("mentorshipEventThreadPoolExecutor")
    public ThreadPoolExecutor mentorshipEventThreadPoolExecutor() {
        return new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
    }
}
