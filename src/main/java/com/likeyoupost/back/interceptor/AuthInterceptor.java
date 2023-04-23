package com.likeyoupost.back.interceptor;

import com.likeyoupost.back.domain.User;
import com.likeyoupost.back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static com.likeyoupost.back.config.ErrorMessage.NOT_AUTH_USER;
import static com.likeyoupost.back.config.LoginConst.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String method = request.getMethod();
        String authentication = request.getHeader(LOGIN_HEADER);
        if(ObjectUtils.isEmpty(authentication) && method.equals("GET")) {
            return true;
        }

        if(ObjectUtils.isEmpty(authentication)) {
            throw new AuthenticationException(NOT_AUTH_USER);
        }
        String[] split = authentication.split(" ");
        String accountType = split[0].toUpperCase();
        Long userId = Long.valueOf(split[1]);
        Optional<User> optionalUser = userRepository.findByIdCache(userId);

        if(!optionalUser.isPresent() || !accountType.equals(
                optionalUser.get().getAccountType().name())) {
            throw new AuthenticationException(NOT_AUTH_USER);
        }

        request.setAttribute(USER_ID, userId);
        request.setAttribute(ACCOUNT_TYPE, accountType);

        return true;
    }
}
