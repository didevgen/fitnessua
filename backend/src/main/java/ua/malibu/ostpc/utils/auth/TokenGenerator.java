package ua.malibu.ostpc.utils.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.malibu.ostpc.models.User;
import ua.malibu.ostpc.services.redis.IRedisRepository;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

@Service
public class TokenGenerator {
    @Autowired
    private IRedisRepository redisRepository;
    private Random random = new SecureRandom();

    public String issueToken(User user) {
        String tokenValue = new BigInteger(130, random).toString(32);
        redisRepository.insert(tokenValue, new LoginToken(user.getEmail(), user.getPassword(), user.getUuid()));
        return tokenValue;
    }

    public IRedisRepository getRedisRepository() {
        return redisRepository;
    }

    public void setRedisRepository(IRedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }
}
