package ua.malibu.ostpc.services.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;
import ua.malibu.ostpc.utils.auth.LoginToken;

import java.util.concurrent.TimeUnit;

@Repository
public class RedisRepository implements IRedisRepository<String, LoginToken> {
    private RedisTemplate<String, LoginToken> redisTemplate;
    private ValueOperations<String, LoginToken> valueOps;

    @Autowired
    public RedisRepository(@Qualifier(value="redisTemplate") RedisTemplate<String, LoginToken> temp) {
        redisTemplate = temp;
        valueOps = redisTemplate.opsForValue();
    }

    @Override
    public LoginToken get(String key) {
        return valueOps.get(key);
    }

    @Override
    public void insert(String key, LoginToken value) {
        valueOps.set(key, value, 1000, TimeUnit.SECONDS);
    }

    @Override
    public void delete(String key) {
        valueOps.getOperations().delete(key);
    }

    @Override
    public void refreshExpirationTime(String key) {
        valueOps.getOperations().expire(key, 1000, TimeUnit.SECONDS);
    }

}
