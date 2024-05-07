package faang.school.achievement.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ExecutorConfig {
    @Value("${executor.threadPoll.size}")
    private int threadPollSize;

    @Bean
    public ExecutorService executorService(){
        return Executors.newFixedThreadPool(threadPollSize);
    }
}
