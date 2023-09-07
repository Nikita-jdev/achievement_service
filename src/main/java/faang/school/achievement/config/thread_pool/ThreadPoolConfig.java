package faang.school.achievement.config.thread_pool;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadPoolConfig {

    @Value("${achievement-service.follower-handler.thread-pool.corePoolSize}")
    private int corePoolSize;
    @Value("${achievement-service.follower-handler.thread-pool.maximumPoolSize}")
    private int maximumPoolSize;
    @Value("${achievement-service.follower-handler.thread-pool.keepAliveTime}")
    private int keepAliveTime;

    @Bean("followerEventThreadPoolExecutor")
    public ThreadPoolExecutor followerEventThreadPoolExecutor() {
        return new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
    }
}
