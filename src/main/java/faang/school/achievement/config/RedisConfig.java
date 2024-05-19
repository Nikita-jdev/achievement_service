package faang.school.achievement.config;

import faang.school.achievement.listener.ProfilePicEventListener;
import faang.school.achievement.listener.SkillEventListener;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {
    private final SkillEventListener skillEventListener;

    private final ProfilePicEventListener profilePicEventListener;

    @Value("${spring.data.redis.port}")
    private int port;

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.channel.skill_channel}")
    private String skillChannel;

    @Value("${spring.data.redis.channel.goal_set_channel.name}")
    private String goalSetChannel;

    @Value("${spring.data.redis.channel.profile_pic_channel}")
    private String profileViewChannel;

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(host, port);
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public ChannelTopic skillChannel() {
        return new ChannelTopic(skillChannel);
    }

    @Bean
    public MessageListenerAdapter skillMessageListenerAdapter() {
        return new MessageListenerAdapter(skillEventListener);
    }

    @Bean
    public ChannelTopic profileViewTopic() {
        return new ChannelTopic(profileViewChannel);
    }

    @Bean
    public MessageListenerAdapter profileViewEventListenerAdapter() {
        return new MessageListenerAdapter(profilePicEventListener);
    }

    @Bean
    public RedisMessageListenerContainer container() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory());
        container.addMessageListener(skillMessageListenerAdapter(), skillChannel());
        container.addMessageListener(new MessageListenerAdapter(goalSetChannel), new ChannelTopic(goalSetChannel));
        container.addMessageListener(profileViewEventListenerAdapter(), profileViewTopic());
        return container;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
