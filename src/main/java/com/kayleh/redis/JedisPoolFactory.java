package com.kayleh.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * RedisConfig，作为Bean自动装配进来，我们还要建立JedisPoolConfig来设置一些参数，供建立JedisPool时读取
 *
 * @Author: Kayleh
 * @Date: 2020/12/3 17:42
 */
@Service
public class JedisPoolFactory
{
    @Autowired
    RedisConfig redisConfig;

    @Bean
    public JedisPool JedisPoolFactory()
    {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(redisConfig.getPoolMaxIdle());
        poolConfig.setMaxTotal(redisConfig.getPoolMaxTotal());
        poolConfig.setMaxWaitMillis(redisConfig.getPoolMaxWait() * 1000);
        return new JedisPool(
                poolConfig,
                redisConfig.getHost(),
                redisConfig.getPort(),
                redisConfig.getTimeout() * 1000,
                redisConfig.getPassword(), 0
        );
    }

}
