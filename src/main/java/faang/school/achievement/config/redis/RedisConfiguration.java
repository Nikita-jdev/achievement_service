package faang.school.achievement.config.redis;

import faang.school.achievement.listener.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@RequiredArgsConstructor
public class RedisConfiguration {
    private final GoalSetListener goalSetListener;
    private final TaskEventListener taskEventListener;
    private final InviteEventListener inviteEventListener;
    private final ProfilePicListener profilePicListener;
    private final CommentEventListener commentEventListener;
    @Value("${spring.data.redis.channels.goal_set_channel.name}")
    private String goalSetChannel;
    @Value("${spring.data.redis.channels.task_channel.name}")
    private String taskChannel;
    @Value("${spring.data.redis.channels.invite_channel.name}")
    private String inviteChannel;
    @Value("${spring.data.redis.channels.profile_pic_channel.name}")
    private String profilePicChannel;
    @Value("${spring.data.redis.channels.comment_channel.name}")
    private String commentChanel;
    @Value("${spring.data.redis.host}")
    private String host;
    @Value("${spring.data.redis.port}")
    private int port;

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
        return new JedisConnectionFactory(config);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(goalSetListener, new ChannelTopic(goalSetChannel));
        container.addMessageListener(taskEventListener, new ChannelTopic(taskChannel));
        container.addMessageListener(inviteEventListener, new ChannelTopic(inviteChannel));
        container.addMessageListener(profilePicListener, new ChannelTopic(profilePicChannel));
        container.addMessageListener(commentEventListener, new ChannelTopic(commentChanel));
        return container;
    }
}
