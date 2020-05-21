package com.sender.sparta.config;

import com.sender.sparta.constant.TokenKey;
import com.sender.sparta.service.TokenService;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@Log4j2
public class TokenExpiredListenerConfig {
    public final static String LISTENER_PATTERN = "__keyevent@*__:expired";

    @Bean
    public RedisMessageListenerContainer listenerContainer(RedisConnectionFactory redisConnection, Executor executor, TokenService tokenService) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        // 设置Redis的连接工厂
        container.setConnectionFactory(redisConnection);
        // 设置监听使用的线程池
        container.setTaskExecutor(executor);
        // 设置监听的Topic: PatternTopic/ChannelTopic
        Topic topic = new PatternTopic(LISTENER_PATTERN);
        // 设置监听器
        container.addMessageListener((message, bytes) -> {
            assert bytes != null;
            String messageStr = new String(message.getBody());
            String channelStr = new String(message.getChannel());
            String patternStr = new String(bytes);
            log.info("\n### redis listener pass ###\n" +
                            "### message: {} ###\n" +
                            "### channel: {} ###\n" +
                            "### pattern: {} ###\n",
                    messageStr, channelStr, patternStr);
            if (messageStr.contains(TokenKey.TOKEN_PREFIX)) {
                tokenService.removeCache(messageStr);
            }
        }, topic);
        return container;
    }

    /**
     * rejection-policy：当pool已经达到max size的时候，如何处理新任务
     * CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
     */
    @Bean
    public Executor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(100);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("V-Thread");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

}
