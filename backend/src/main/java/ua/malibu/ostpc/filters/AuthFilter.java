package ua.malibu.ostpc.filters;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import ua.malibu.ostpc.exceptions.rest.RestException;
import ua.malibu.ostpc.services.redis.RedisRepository;
import ua.malibu.ostpc.utils.auth.LoginToken;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class AuthFilter extends OncePerRequestFilter {
    private static final Logger logger = Logger.getLogger(AuthFilter.class);

    @Autowired
    private RedisRepository redisRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String curToken = httpServletRequest.getHeader("token");
        String uuid = redisRepository.get(curToken);
        if (uuid == null) {
            logger.info("Token " + curToken + " has expired");
            throw new RestException(HttpStatus.UNAUTHORIZED, 40001, "Check credentials!");
        } else {
            redisRepository.refreshExpirationTime(curToken);
            Authentication auth = new LoginToken(uuid);
            SecurityContextHolder.getContext().setAuthentication(auth);

            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }
}