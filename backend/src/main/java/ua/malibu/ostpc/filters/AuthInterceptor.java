package ua.malibu.ostpc.filters;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.*;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ua.malibu.ostpc.exceptions.rest.RestException;
import ua.malibu.ostpc.services.redis.IRedisRepository;
import ua.malibu.ostpc.utils.auth.LoginToken;

@Component
public class AuthInterceptor extends ChannelInterceptorAdapter {
    private static final Logger logger = Logger.getLogger(AuthInterceptor.class);

    @Autowired
    private IRedisRepository<String, LoginToken> redisRepository;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if (accessor.getMessageType().equals(SimpMessageType.MESSAGE)) {
            String curToken = (String) accessor.getHeader("x-auth-token");
            if (curToken == null) {
                logger.info("Token " + curToken + " not found");
                //throw new RestException(HttpStatus.UNAUTHORIZED, 404001, "Token not found!");
                return null;
            }
            LoginToken loginToken = redisRepository.get(curToken);
            if (loginToken == null) {
                logger.info("Token " + curToken + " has expired");
                //throw new RestException(HttpStatus.UNAUTHORIZED, 404001, "Token not found!");
                return null;
            } else {
                redisRepository.refreshExpirationTime(curToken);
                SecurityContextHolder.getContext()
                        .setAuthentication(loginToken);
                return message;
            }
        }
    }

    public IRedisRepository getIRedisRepository() {
        return redisRepository;
    }

    public void setIRedisRepository(IRedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }

}
