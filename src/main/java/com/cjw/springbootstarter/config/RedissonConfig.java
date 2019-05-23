package com.cjw.springbootstarter.config;

import com.cjw.springbootstarter.util.Log4JUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * redisson的配置，用于分布式锁的实现
 */
@Configuration
public class RedissonConfig {


    /**
     * redisson客户端 (destroyMethod = "shutdown")
     */
    @Bean
    public RedissonClient redissonClient() {
        Config config = null;
        try {
            config = Config.fromYAML(new ClassPathResource("redisson.yml").getInputStream());
        } catch (Exception ex) {
            Log4JUtils.getLogger().info("莫名的错误，但不影响整个项目");
        }
        return Redisson.create(config);
    }

}
