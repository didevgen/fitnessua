package ua.malibu.ostpc.utils.auth;

import ua.malibu.ostpc.services.redis.RedisRepository;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class TokenGenerator {
    private RedisRepository redisRepository; //  Warning! not initialized!
    private Random random = new SecureRandom();

    public String issueToken(String uuid) {
        String tokenValue = new BigInteger(130, random).toString(32);
        redisRepository.insert(tokenValue, uuid);
        return tokenValue;
    }
}
