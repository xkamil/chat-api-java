package com.xkamil.middleware;

import com.xkamil.handlers.exceptions.AuthenticationException;
import com.xkamil.handlers.exceptions.ItemNotFoundException;
import com.xkamil.repositories.TokenRepository;
import com.xkamil.storage.Token;
import com.xkamil.storage.User;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
    private static final int TOKEN_LIFE_TIME = 3000000;

    private TokenRepository tokenRepository;

    public AuthorizationInterceptor(TokenRepository tokenRepository){
        super();
        this.tokenRepository = tokenRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ItemNotFoundException, AuthenticationException {

        String tokenHeader = request.getHeader("token");

        if(tokenHeader == null){
            throw new AuthenticationException("No token found in header.");
        }

        User user = tokenRepository.findUserByToken(tokenHeader);
        Token token = tokenRepository.findByToken(tokenHeader);

        if(user == null ){
            throw new AuthenticationException("User or token not found");
        }

        if(token == null || isTokenExpired(token)){
            throw new AuthenticationException("Bad or expired token");
        }

        tokenRepository.refreshToken(tokenHeader);

        request.setAttribute("user", user);

        return true;
    }

    private boolean isTokenExpired(Token token){
        Long currentDate = new Date().getTime();
        Long tokenExpirationDate = token.getUpdatedAt() + TOKEN_LIFE_TIME;

        System.out.println("Token expires in " + (currentDate - tokenExpirationDate));

        return tokenExpirationDate < currentDate;
    }

}
