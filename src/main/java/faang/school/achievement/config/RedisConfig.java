package faang.school.achievement.config;

import faang.school.achievement.listener.MentorshipStartEventListener;
import faang.school.achievement.listener.SkillEventListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class RedisConfig {
    @Value("${spring.data.redis.host}")
    private String host;
    @Value("${spring.data.redis.port}")
    private int port;
    @Value("${spring.data.redis.channels.skill}")
    private String skillChannel;
    @Value("${spring.data.redis.channels.mentorship_channel}")
    private String mentorshipChannel;
    @Value("${spring.data.redis.channels.post_channel.name}")
    private String postChannel;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisConfiguration = new RedisStandaloneConfiguration(host, port);
        log.info("Created new redis connection factory with host: {}, port: {}", host, port);
        return new JedisConnectionFactory(redisConfiguration);
    }

    @Bean(name = "skillAdapter")
    public MessageListenerAdapter skillAdapter(SkillEventListener skillEventListener) {
        return new MessageListenerAdapter(skillEventListener);
    }

    @Bean(name = "mentorshipAdapter")
    public MessageListenerAdapter mentorshipAdapter(MentorshipStartEventListener mentorshipEventListener) {
        return new MessageListenerAdapter(mentorshipEventListener);
    }

    @Bean(name = "postAdapter")
    public MessageListenerAdapter postAdapter(MentorshipStartEventListener postEventListener) {
        return new MessageListenerAdapter(postEventListener);
    }

    @Bean
    public ChannelTopic skillTopic() {
        return new ChannelTopic(skillChannel);
    }

    @Bean
    public ChannelTopic mentorshipTopic() {
        return new ChannelTopic(mentorshipChannel);
    }

    public ChannelTopic postTopic() {
        return new ChannelTopic(postChannel);
    }

    @Bean
    public RedisMessageListenerContainer redisContainer(@Qualifier("skillAdapter") MessageListenerAdapter skillAdapter,
                                                        @Qualifier("mentorshipAdapter") MessageListenerAdapter mentorshipAdapter,
                                                        @Qualifier("postAdapter") MessageListenerAdapter postAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory());
        container.addMessageListener(skillAdapter, skillTopic());
        container.addMessageListener(mentorshipAdapter, mentorshipTopic());
        container.addMessageListener(postAdapter, postTopic());
        return container;
    }
}