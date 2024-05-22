package faang.school.achievement.config;

import faang.school.achievement.listener.CommentEventListener;
import faang.school.achievement.listener.LikeEventListener;
import faang.school.achievement.listener.ProjectEventListener;
import faang.school.achievement.listener.MentorshipEventListener;
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
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;
    @Value("${spring.data.redis.port}")
    private int port;

    @Value("${spring.data.redis.channels.notification_like_channel.name}")
    private String notificationLikeTopic;
    @Value("${spring.data.redis.channels.project_channel.name}")
    private String projectTopic;
    @Value("${spring.data.redis.channels.mentorship_channel.name}")
    private String mentorshipEventTopic;
    @Value("${spring.data.redis.channels.comment_channel.name}")
    private String commentTopic;

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
        return new JedisConnectionFactory(config);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }

    @Bean
    public MessageListenerAdapter likeEventAdapter(LikeEventListener likeEventListener) {
        return new MessageListenerAdapter(likeEventListener);
    }

    @Bean
    public MessageListenerAdapter projectListener(ProjectEventListener projectEventListener) {
        return new MessageListenerAdapter(projectEventListener);
    }

    @Bean
    MessageListenerAdapter mentorshipListener(MentorshipEventListener mentorshipEventListener) {
        return new MessageListenerAdapter(mentorshipEventListener);
    }

    @Bean
    MessageListenerAdapter commentListener(CommentEventListener commentEventListener) {
        return new MessageListenerAdapter(commentEventListener);
    }


    @Bean
    public ChannelTopic likeEventTopic() {
        return new ChannelTopic(notificationLikeTopic);
    }

    @Bean
    public ChannelTopic projectTopic() {
        return new ChannelTopic(projectTopic);
    }

    @Bean
    ChannelTopic mentorshipEventTopic() {
        return new ChannelTopic(mentorshipEventTopic);
    }

    @Bean
    ChannelTopic commentEventTopic() {
        return new ChannelTopic(commentTopic);
    }

    @Bean
    public RedisMessageListenerContainer redisContainer(MessageListenerAdapter likeEventAdapter,
                                                        MessageListenerAdapter projectListener,
                                                        MessageListenerAdapter mentorshipListener,
                                                        MessageListenerAdapter commentListener) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory());
        container.addMessageListener(likeEventAdapter, likeEventTopic());
        container.addMessageListener(projectListener, projectTopic());
        container.addMessageListener(mentorshipListener, mentorshipEventTopic());
        container.addMessageListener(commentListener, commentEventTopic());
        return container;
    }
}
