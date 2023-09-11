package faang.school.achievement.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import faang.school.achievement.listener.GoalEventListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import faang.school.achievement.messaging.listener.MentorshipStartListener;
import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;



@Configuration
@RequiredArgsConstructor
public class RedisConfig {
    @Value("${spring.data.redis.host}")
    private final String host;
    @Value("${spring.data.redis.port}")
    private final int port;
  
    @Value("${spring.data.redis.channel.goal_channel.name}")
    private String goalChannelName;

    @Value("${spring.data.redis.channel.mentorship}")
    private final String mentorshipChannelName;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
        return new JedisConnectionFactory(config);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }

    @Bean
    MessageListenerAdapter goalSetListener(GoalEventListener goalEventListener) {
        return new MessageListenerAdapter(goalEventListener);
    }

    @Bean
    ChannelTopic goalChannel() {
        return new ChannelTopic(goalChannelName);
    }
  
    @Bean
    public MessageListenerAdapter mentorshipListener(MentorshipStartListener mentorshipStartListener) {
        return new MessageListenerAdapter(mentorshipStartListener);
    }

    @Bean
    public ChannelTopic mentorshipChannel() {
        return new ChannelTopic(mentorshipChannelName);
    }
  

    @Bean
    RedisMessageListenerContainer redisContainer(MessageListenerAdapter goalSetListener, MessageListenerAdapter mentorshipListener) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory());
        container.addMessageListener(goalSetListener, goalChannel());
        container.addMessageListener(mentorshipListener, mentorshipChannel());
        return container;
    }

}
