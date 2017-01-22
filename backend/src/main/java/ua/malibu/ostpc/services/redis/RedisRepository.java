package ua.malibu.ostpc.services.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Repository
@Transactional
public class RedisRepository implements IRedisRepository<String, String> {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Resource(name="redisTemplate")
    private ValueOperations<String, String> valueOps;

    @Override
    public void insert(String key, String value) {
        valueOps.set(key, value, 10, TimeUnit.SECONDS);
    }

    @Override
    public void delete(String key) {
        valueOps.getOperations().delete(key);
    }

    @Override
    public void refreshExpirationTime(String key) {
        valueOps.getOperations().expire(key, 10, TimeUnit.SECONDS);
    }
}
